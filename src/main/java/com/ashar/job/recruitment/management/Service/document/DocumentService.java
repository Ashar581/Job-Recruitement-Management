package com.ashar.job.recruitment.management.Service.document;

import com.ashar.job.recruitment.management.Dto.DocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    DocumentDto upload(MultipartFile file, String email, UUID uuid);
    DocumentDto get(UUID uuid);
    List<DocumentDto> getAll();
    DocumentDto delete(UUID uuid);
    List<DocumentDto> getAllUserDocuments(String email);

    DocumentDto getDocumentByIdAndJobCode(UUID uuid, String jobCode);
}
