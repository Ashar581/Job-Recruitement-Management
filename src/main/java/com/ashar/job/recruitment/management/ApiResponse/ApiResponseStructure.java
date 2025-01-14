package com.ashar.job.recruitment.management.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseStructure<T>{
    private String message;
    private boolean status;
    private T data;
}
