package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID>{
    Optional<List<Candidate>> findByEmail(String email);

}
