package com.ashar.job.recruitment.management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class JobOpening {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String code;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Double preferredExperience;
    private List<String> requiredSkills;
    private List<String> additionalSkills;
    @ManyToMany(mappedBy = "jobOpenings")
    private List<Candidate> candidates;

    @PrePersist
    public void onPrePersist(){
        this.code = "JOB-"+Date.from(Instant.now()).hashCode();
    }
}
