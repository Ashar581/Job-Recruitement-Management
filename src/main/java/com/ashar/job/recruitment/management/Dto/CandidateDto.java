package com.ashar.job.recruitment.management.Dto;

import com.ashar.job.recruitment.management.Entity.Candidate;
import com.ashar.job.recruitment.management.Entity.JobOpening;
import com.ashar.job.recruitment.management.Model.Experience;
import com.ashar.job.recruitment.management.Model.Project;
import com.ashar.job.recruitment.management.Model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateDto {
    private UUID uuid;
    private String name;
    private String email;
    private String phone;
    private String summary;
    @JsonProperty("primary_skills")
    private List<String> primarySkills;
    private List<Project> projects;
    private Integer months;
    private Integer years;
    private Date appliedOn;
    private Double score;
    private Status status;
    private List<JobOpeningDto> jobOpeningDto;

    public static Candidate dtoToEntity(CandidateDto dto){
        Candidate entity = new Candidate();

        entity.setUuid(dto.getUuid());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setSummary(dto.getSummary());
        entity.setAppliedOn(dto.getAppliedOn());
        entity.setProjects(dto.getProjects());
        entity.setExperience(new Experience(dto.getMonths(),dto.getYears()));
        entity.setPrimarySkills(dto.getPrimarySkills());
        entity.setScore(dto.getScore());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    public static CandidateDto entityToDto(Candidate entity){
        CandidateDto dto = new CandidateDto();

        dto.setUuid(entity.getUuid());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setSummary(entity.getSummary());
        dto.setAppliedOn(entity.getAppliedOn());
        dto.setProjects(entity.getProjects());
        dto.setMonths(entity.getExperience().getMonths());
        dto.setYears(entity.getExperience().getYears());
        dto.setPrimarySkills(entity.getPrimarySkills());
        dto.setScore(entity.getScore());
        dto.setStatus(entity.getStatus());
        dto.setJobOpeningDto(entity.getJobOpenings().stream().map(job -> JobOpeningDto.entityToDto(job)).toList());

        return dto;
    }
}
