package com.ashar.job.recruitment.management.Controller;

import com.ashar.job.recruitment.management.ApiResponse.BaseApiResponse;
import com.ashar.job.recruitment.management.Service.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/document")
public class DocumentController extends BaseApiResponse {
    @Autowired
    private DocumentService documentService;
    @PostMapping("")
    public ResponseEntity upload(@RequestParam("file")MultipartFile file, @RequestParam(value = "email",required = false)String email, @RequestParam(value = "uuid",required = false)UUID uuid){
        return sendSuccessfulApiResponse(documentService.upload(file,email,uuid),"Document uploaded successfully.");
    }
    @GetMapping("{uuid}")
    public ResponseEntity get(@PathVariable("uuid")UUID uuid){
        return sendSuccessfulApiResponse(documentService.get(uuid),"Document viewed.");
    }
    @GetMapping("user/{email}")
    public ResponseEntity getAllUserDocuments(@PathVariable("email")String email){
        return sendSuccessfulApiResponse(documentService.getAllUserDocuments(email),"User document list");
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        return sendSuccessfulApiResponse(documentService.getAll(),"All Documents");
    }
    @DeleteMapping("{uuid}")
    public ResponseEntity delete(@PathVariable("uuid")UUID uuid){
        return sendSuccessfulApiResponse(documentService.delete(uuid),"Document deleted.");
    }
    @GetMapping("candidate/{uuid}/job/{jobCode}")
    public ResponseEntity getDocumentByCandidateIdAndJobCode(@PathVariable("uuid")UUID candidateUuid, @PathVariable("jobCode")String jobCode){
        return sendSuccessfulApiResponse(documentService.getDocumentByIdAndJobCode(candidateUuid,jobCode),"Document viewed");
    }
}
