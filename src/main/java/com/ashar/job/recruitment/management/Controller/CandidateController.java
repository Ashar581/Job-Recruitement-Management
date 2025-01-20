package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Dto.CandidateDto;
import com.ashar.job.recruitment.management.Service.candidate.CandidateService;
import com.ashar.job.recruitment.management.Service.resumeUtil.ResumeUtilService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/candidate")
public class CandidateController extends BaseApiResponse {
    @Autowired
    private ResumeUtilService resumeUtilService;
    @Autowired
    private CandidateService candidateService;
    @PostMapping("apply")
    public ResponseEntity parseDocument(@RequestParam("file") MultipartFile file,@RequestParam("jobCode")String jobCode){
//        return sendSuccessfulApiResponse(null,"Application in on process");

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
    @GetMapping("{email}")
    public ResponseEntity getCandidateApplies(@PathVariable("email") String email){
        return sendSuccessfulApiResponse(candidateService.getCandidateApplicants(email),"All applications.");
    }
    @GetMapping("{uuid}/view")
    public ResponseEntity viewCandidateById(@PathVariable("uuid")UUID uuid){
        return sendSuccessfulApiResponse(candidateService.viewById(uuid),"Candidate View.");
    }
}
