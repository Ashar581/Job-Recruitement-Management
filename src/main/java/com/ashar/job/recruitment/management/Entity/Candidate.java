package com.ashar.job.recruitment.management.Entity;

import com.ashar.job.recruitment.management.Model.Experience;
import com.ashar.job.recruitment.management.Model.Project;
import com.ashar.job.recruitment.management.Model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String name;
    private String email;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String summary;
    @JsonProperty("primary_skills")
    private List<String> primarySkills;
    @Embedded
    @ElementCollection
    private List<Project> projects;
    @Embedded
    private Experience experience;
    //jobOpening ManyToMany
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "candidate_job_opening",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_opening_id")
    )
    private List<JobOpening> jobOpenings;
    private Date appliedOn;
    private Double score;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Document> documents;

}
