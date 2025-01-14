package com.ashar.job.recruitment.management.Service.candidate;

import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {
    boolean apply(MultipartFile file);
}
