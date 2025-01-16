package com.ashar.job.recruitment.management.Service.candidate;

import com.ashar.job.recruitment.management.Dto.CandidateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    boolean apply(MultipartFile file, String jobCode);

    List<CandidateDto> getAll(String jobCode);
}
