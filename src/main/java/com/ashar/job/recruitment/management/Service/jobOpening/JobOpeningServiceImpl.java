package com.ashar.job.recruitment.management.Service.jobOpening;

import com.ashar.job.recruitment.management.Dto.JobOpeningDto;
import com.ashar.job.recruitment.management.Entity.JobOpening;
import com.ashar.job.recruitment.management.Exception.NotFoundException;
import com.ashar.job.recruitment.management.Exception.NullException;
import com.ashar.job.recruitment.management.Exception.WrongInputLogicException;
import com.ashar.job.recruitment.management.Repository.JobOpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobOpeningServiceImpl implements JobOpeningService{
    @Autowired
    private JobOpeningRepository jobRepository;
    @Override
    public boolean createJobOpening(JobOpeningDto dto) {
        if (dto.getTitle()==null || dto.getTitle().isEmpty()) throw new NullException("Title cannot be empty");
        if (dto.getDescription()==null ||  dto.getDescription().isEmpty()) throw new NullException("Description cannot be empty.");
        if (dto.getStartDate().before(Date.from(Instant.now()))) throw new WrongInputLogicException("Start date cannot be before today.");
        if (dto.getEndDate().before(dto.getStartDate())) throw new WrongInputLogicException("End date cannot be before start date");
        try {
            JobOpening jobOpening = JobOpeningDto.dtoToEntity(dto);
            jobRepository.save(jobOpening);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public JobOpeningDto deleteJobOpening(String code) throws NullException, NotFoundException {
        if (code==null) throw new NullException("Job Code cannot be empty.");
        JobOpening jobOpening = jobRepository.findByCode(code)
                .orElseThrow(()->new NotFoundException("The Job Opening does not exists"));
        jobRepository.delete(jobOpening);

        return JobOpeningDto.entityToDto(jobOpening);
    }

    @Override
    public List<JobOpeningDto> getAllJobOpening() {
        return jobRepository.findAll().stream()
                .map(job -> {
                    return JobOpeningDto.entityToDto(job);
                }).collect(Collectors.toList());
    }

    @Override
    public JobOpeningDto getJobOpening(String code) {
        if (code==null || code.isEmpty()) throw new NullException("Job code cannot be empty.");
        return JobOpeningDto.entityToDto(jobRepository.findByCode(code)
                .orElseThrow(()->new NotFoundException("Job Opening does not exists")));
    }
}
