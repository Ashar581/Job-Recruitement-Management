package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Dto.DocumentDto;
import com.ashar.job.recruitment.management.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    @Query(value = "SELECT uuid,metadata,file_name,file_type FROM document", nativeQuery = true)
    Optional<List<DocumentDto>> findAllDocumentWithoutBytes();
}
