package com.ashar.job.recruitment.management.Service.candidate;

import com.ashar.job.recruitment.management.Dto.CandidateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CandidateService {
    boolean apply(MultipartFile file, String jobCode);
    List<CandidateDto> getAll();
    CandidateDto update(CandidateDto dto);
    List<CandidateDto> getCandidateApplicants(String email);

    CandidateDto viewById(UUID uuid);
}
