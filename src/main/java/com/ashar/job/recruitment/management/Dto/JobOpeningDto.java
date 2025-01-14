package com.ashar.job.recruitment.management.Dto;

import com.ashar.job.recruitment.management.Entity.JobOpening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOpeningDto {
    private UUID uuid;
    private String code;
    @NotNull(message = "Title cannot be empty.")
    private String title;
    @NotNull(message = "Description cannot be empty.")
    private String description;
    private Date startDate;
    @NotNull(message = "End date cannot be empty.")
    private Date endDate;
    private Integer preferredExperience;
    private List<String> requiredSkills;
    private List<String> additionalSkills;

    public static JobOpening dtoToEntity(JobOpeningDto dto){
        JobOpening entity = new JobOpening();
        entity.setCode(dto.getCode());
        entity.setEndDate(dto.getEndDate());
        entity.setStartDate(dto.getStartDate());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPreferredExperience(dto.getPreferredExperience());
        entity.setRequiredSkills(dto.getRequiredSkills());
        entity.setAdditionalSkills(dto.getAdditionalSkills());

        return entity;
    }

    public static JobOpeningDto entityToDto(JobOpening entity){
        JobOpeningDto dto = new JobOpeningDto();
        dto.setUuid(entity.getUuid());
        dto.setCode(entity.getCode());
        dto.setEndDate(entity.getEndDate());
        dto.setStartDate(entity.getStartDate());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setPreferredExperience(entity.getPreferredExperience()==null?0 : entity.getPreferredExperience());
        dto.setRequiredSkills(entity.getRequiredSkills()==null?new ArrayList<>() : entity.getRequiredSkills());
        dto.setAdditionalSkills(entity.getAdditionalSkills()==null?new ArrayList<>() : entity.getAdditionalSkills());

        return dto;
    }
}
