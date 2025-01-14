package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Entity.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobOpeningRepository extends JpaRepository<JobOpening, UUID> {
    Optional<JobOpening> findByCode(String code);
}
