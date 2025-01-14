package com.ashar.job.recruitment.management.Service.resumeUtil;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeUtilService {
    String convertDocumentToText(MultipartFile file);
    Double resumeScore(List<String> skillList);
}
