package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
