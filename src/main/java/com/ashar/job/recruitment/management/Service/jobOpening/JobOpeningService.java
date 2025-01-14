package com.ashar.job.recruitment.management.Service.jobOpening;

import com.ashar.job.recruitment.management.Dto.JobOpeningDto;

import java.util.List;

public interface JobOpeningService {
    boolean createJobOpening(JobOpeningDto dto);
    JobOpeningDto deleteJobOpening(String code);
    List<JobOpeningDto> getAllJobOpening();
    JobOpeningDto getJobOpening(String code);
}
