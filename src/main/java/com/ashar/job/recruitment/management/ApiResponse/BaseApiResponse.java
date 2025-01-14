package com.ashar.job.recruitment.management.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseApiResponse<T>{
    public ResponseEntity<ApiResponseStructure<T>> sendSuccessfulApiResponse(T data, String message){
        ApiResponseStructure structure = new ApiResponseStructure();
        structure.setData(data);
        structure.setStatus(true);
        structure.setMessage(message);
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponseStructure<T>> sendFailedApiResponse(String message, HttpStatus status){
        ApiResponseStructure structure = new ApiResponseStructure();
        structure.setMessage(message);

        return new ResponseEntity<>(structure,status);
    }
}
