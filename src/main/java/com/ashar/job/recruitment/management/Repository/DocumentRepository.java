package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Dto.DocumentDto;
import com.ashar.job.recruitment.management.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    @Query(value = "SELECT uuid,metadata,file_name,file_type FROM document", nativeQuery = true)
    Optional<List<Document>> findAllDocumentWithoutBytes();

    @Query("SELECT d FROM Document d " +
            "JOIN d.candidate c " +
            "JOIN c.jobOpenings j " +
            "WHERE c.uuid = :candidateId AND j.code = :jobCode")
    Optional<Document> findByCandidateIdAndJobOpeningCode(UUID candidateId, String jobCode);


}
