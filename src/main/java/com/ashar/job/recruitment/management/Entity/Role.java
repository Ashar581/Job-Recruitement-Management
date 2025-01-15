package com.ashar.job.recruitment.management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String roleName;
    private String roleCode;
    //ManyToOne
    @ManyToMany(mappedBy = "roles")
    private List<User> user;
    @PrePersist
    public void onPrePersist(){
        this.roleCode = this.roleName.toUpperCase().replaceAll("[^a-zA-Z]","");
    }
}
