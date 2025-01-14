package com.ashar.job.recruitment.management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String,Object> metadata;

    private String fileName;
    private byte [] data;
    private String fileType;
    //ManyToOne
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //ManyToOne
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
