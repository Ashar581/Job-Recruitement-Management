package com.ashar.job.recruitment.management.Service.converter;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentConverter {
    String extractText(MultipartFile file) throws IOException;
}
