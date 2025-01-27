package com.ashar.job.recruitment.management.Service.candidate;

import com.ashar.job.recruitment.management.Dto.CandidateDto;
import com.ashar.job.recruitment.management.Entity.*;
import com.ashar.job.recruitment.management.Event.CandidateScoreEvent;
import com.ashar.job.recruitment.management.Exception.*;
import com.ashar.job.recruitment.management.Model.Status;
import com.ashar.job.recruitment.management.Repository.*;
import com.ashar.job.recruitment.management.Service.llm.LlmService;
import com.ashar.job.recruitment.management.Service.normalization.NormalizationService;
import com.ashar.job.recruitment.management.Service.resumeUtil.ResumeUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService{
    @Autowired
    private ResumeUtilService resumeUtilService;
    @Autowired
    private NormalizationService normalizationService;
    @Autowired
    private LlmService llmService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobOpeningRepository jobOpeningRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Override
    @Transactional
    public boolean apply(MultipartFile file, String jobCode) throws DataConversionException, NotFoundException{
        JobOpening jobOpening = jobOpeningRepository.findByCode(jobCode)
                .orElseThrow(()->new NotFoundException("No job opening found."));
        if (jobOpening.getEndDate().before(Date.from(Instant.now()))){
            throw new ExpirationException("Company is no longer allowing applications.");
        }
        String convertedToNormalizedText = resumeUtilService.convertDocumentToText(file);

        //generate summary metadata of the resume
        Map<String,Object> metadata = llmService.cvSummaryGenerator(convertedToNormalizedText);
        //converting to candidate entity object...
        Candidate candidate;
        try {
            candidate = objectMapper.convertValue(metadata,Candidate.class);
            candidate.setAppliedOn(Date.from(Instant.now()));
        }catch (Exception e){
            throw new ParsingException(e.getMessage());
        }
        String candidateEmail = candidate.getEmail();
        if (candidateEmail==null || candidateEmail.isEmpty()){
            throw new FailedProcessException("You application could not be processed.");
        }
        if (jobOpening.getCandidates().stream()
                .anyMatch(applied -> applied.getEmail().equalsIgnoreCase(candidateEmail))){
            throw new ConflictException("Candidate has already applied.");
        }
        //add the document
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setMetadata(metadata);
        //set the candidate
        try {
            document.setData(file.getBytes());
        }catch (Exception e){
            throw new DataConversionException("Failed to get the file data.");
        }
        //add document to candidate
        candidate.setDocuments(Arrays.asList(document));
        candidate.setStatus(Status.APPLIED);
        candidate.setJobOpenings(new ArrayList<>());
        candidate.getJobOpenings().add(jobOpening);
        //add job opening to candidate
//        candidate.setJobOpenings(Arrays.asList(jobOpening));
        //save candidate
        candidate = candidateRepository.save(candidate);
        //save document with the candidate
        document.setCandidate(candidate);
        document = documentRepository.save(document);
        //set candidate in job opening
        jobOpening.getCandidates().add(candidate);
        //save job
        jobOpeningRepository.save(jobOpening);
        //set user
        //create user if no user exists.
        User user = userRepository.findByEmail(candidateEmail)
                .orElse(null);
        if (user==null) {
            user = new User();
            user.setName(candidate.getName());
            //set document
            user.setDocuments(new ArrayList<>());
            user.getDocuments().add(document);
            //set role as CANDIDATE
            user.setEmail(candidate.getEmail());
            user.setPhone(candidate.getPhone());
            //set default password. Also, make sure to hash the password.
            user.setPassword(passwordEncoder.encode("Default@2025"));
            Role role = roleRepository.findByRoleCode("CANDIDATE")
                    .orElseThrow(() -> new NotFoundException("No role was mapped. Error processing account creation."));
            user.setRoles(Set.of(role));
        } else{
            if (user.getDocuments() == null) {
                user.setDocuments(new ArrayList<>());
            }
            user.getDocuments().add(document);
            Role role = roleRepository.findByRoleCode("CANDIDATE")
                    .orElseThrow(() -> new NotFoundException("No role was mapped. Error processing account creation."));
            if (user.getRoles()!=null){
                user.getRoles().add(role);
            }else {
                user.setRoles(Set.of(role));
            }
        }
        //save user
        userRepository.save(user);
        document.setUser(user);
        documentRepository.save(document);
        eventPublisher.publishEvent(new CandidateScoreEvent(candidate.getUuid(),document.getMetadata(),jobCode));
        return true;
    }

    @Override
    public List<CandidateDto> getAll() {
        return candidateRepository.findAll()
                .stream()
                .map(c -> CandidateDto.entityToDto(c))
                .toList();
    }

    @Transactional
    @Override
    public CandidateDto update(CandidateDto dto) {
        if (dto.getUuid()==null) throw new NullException("User ID cannot be empty.");
        Candidate candidate = candidateRepository.findById(dto.getUuid())
                .orElseThrow(()->new NotFoundException("Application was not found."));
        if (dto.getScore()!=null){
            candidate.setScore(dto.getScore());
        }
        if (dto.getStatus()!=null){
            candidate.setStatus(dto.getStatus());
        }
        Hibernate.initialize(candidate.getJobOpenings());
        return CandidateDto.entityToDto(candidateRepository.save(candidate));
    }

    @Override
    public List<CandidateDto> getCandidateApplicants(String email) {
        return candidateRepository.findByEmail(email)
                .orElse(null)
                .stream()
                .filter(f -> f!=null)
                .map(application -> CandidateDto.entityToDto(application))
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDto viewById(UUID uuid) {
        return CandidateDto.entityToDto(candidateRepository.findById(uuid)
                .orElseThrow(()->new NotFoundException("Candidate now found.")));
    }
}
