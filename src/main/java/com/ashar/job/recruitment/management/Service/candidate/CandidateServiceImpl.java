package com.ashar.job.recruitment.management.Service.candidate;

import com.ashar.job.recruitment.management.Entity.Candidate;
import com.ashar.job.recruitment.management.Entity.Document;
import com.ashar.job.recruitment.management.Entity.Role;
import com.ashar.job.recruitment.management.Entity.User;
import com.ashar.job.recruitment.management.Exception.DataConversionException;
import com.ashar.job.recruitment.management.Service.llm.LlmService;
import com.ashar.job.recruitment.management.Service.normalization.NormalizationService;
import com.ashar.job.recruitment.management.Service.resumeUtil.ResumeUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;

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
    @Override
    public boolean apply(MultipartFile file) throws DataConversionException{
        String convertedToNormalizedText = resumeUtilService.convertDocumentToText(file);

        //generate summary metadata of the resume
        Map<String,Object> metadata = llmService.cvSummaryGenerator(convertedToNormalizedText);
        //converting to candidate entity object...
        Candidate candidate = new Candidate();
        try {
            candidate = objectMapper.convertValue(metadata,Candidate.class);
            candidate.setAppliedOn(Date.from(Instant.now()));
        }catch (Exception e){
            System.err.println(e.getMessage());
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
        //set user

        //create user
        User user = new User();
        user.setName(candidate.getName());
        //set document
        //set role as CANDIDATE
        user.setEmail(candidate.getEmail());
        user.setPhone(candidate.getPhone());
        //set default password. Also, make sure to hash the password.
        user.setPassword("Default@2025");
        //temporary role object creation since the db is yet to be configured.
        Role role = new Role();
        role.setRoleName("candidate");
        role.setRoleCode("CANDIDATE");
        user.setRoles(Set.of(role));

        System.out.println("Candidate Object:\n"+candidate);
        System.out.println("User Object:\n"+user);
        System.out.println("Document Object:\n"+document);

        return true;
    }
}
