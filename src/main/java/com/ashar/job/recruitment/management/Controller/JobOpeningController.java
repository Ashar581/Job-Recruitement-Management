package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Dto.JobOpeningDto;
import com.ashar.job.recruitment.management.Service.jobOpening.JobOpeningService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
public class JobOpeningController extends BaseApiResponse {
    @Autowired
    private JobOpeningService jobOpeningService;

    @GetMapping("{code}")
    public ResponseEntity getJob(@PathVariable("code")String code){
        return sendSuccessfulApiResponse(jobOpeningService.getJobOpening(code),"Job Opening Details");
    }
    @GetMapping("/all")
    public ResponseEntity getAllJobs(){
        return sendSuccessfulApiResponse(jobOpeningService.getAllJobOpening(),"List of all job opening");
    }
    @PostMapping("create")
    public ResponseEntity createJobOpening(@Valid @RequestBody JobOpeningDto dto){
        if (jobOpeningService.createJobOpening(dto)){
            return sendSuccessfulApiResponse(true,"Job Opening was created.");
        }
        return sendFailedApiResponse("Job Opening was not created.", HttpStatus.PRECONDITION_FAILED);
    }
    @DeleteMapping("{code}")
    public ResponseEntity deleteJobOpening(@PathVariable("code") String code){
        return sendSuccessfulApiResponse(jobOpeningService.deleteJobOpening(code),"The Job Opening was removed.");
    }
}
