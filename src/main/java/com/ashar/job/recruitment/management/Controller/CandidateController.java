package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Service.candidate.CandidateService;
import com.ashar.job.recruitment.management.Service.resumeUtil.ResumeUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
//        return sendSuccessfulApiResponse(resumeUtilService.convertDocumentToText(file),"Your Resume was parsed successfully");
    }
}
