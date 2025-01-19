package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Dto.CandidateDto;
import com.ashar.job.recruitment.management.Service.candidate.CandidateService;
import com.ashar.job.recruitment.management.Service.resumeUtil.ResumeUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/candidate")
public class CandidateController extends BaseApiResponse {
    @Autowired
    private ResumeUtilService resumeUtilService;
    @Autowired
    private CandidateService candidateService;
    @PostMapping("apply")
    public ResponseEntity parseDocument(@RequestParam("file") MultipartFile file,@RequestParam("jobCode")String jobCode){
        return sendSuccessfulApiResponse(candidateService.apply(file,jobCode),"Application in on process");
    }
    @GetMapping("/all")
    public ResponseEntity getAllCandidate(@RequestParam(value = "byJob",required = false)boolean byJob,
                                          @RequestParam(value = "jobCode",required = false)String jobCode){
        return sendSuccessfulApiResponse(candidateService.getAll(),"All Candidate View.");
    }

    @PutMapping("")
    public ResponseEntity updateCandidate(@RequestBody CandidateDto dto){
        return sendSuccessfulApiResponse(candidateService.update(dto),"Candidate updated");
    }
}
