package com.ashar.job.recruitment.management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> globalExceptionHandler(Exception e){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",e.getMessage());
        exceptionHandlerResponse.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.BAD_GATEWAY);
    }
    @ExceptionHandler(NullException.class)
    public ResponseEntity<Map<String,Object>> nullExceptionHandler(NullException nullException){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",nullException.getMessage());
        exceptionHandlerResponse.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ParsingException.class)
    public ResponseEntity<Map<String,Object>> parsingExceptionHandler(ParsingException parsingException){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",parsingException.getMessage());
        exceptionHandlerResponse.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse,HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(DataConversionException.class)
    public ResponseEntity<Map<String,Object>> dataConversionExceptionHandler(DataConversionException dataConversionException){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",dataConversionException.getMessage());
        exceptionHandlerResponse.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(WrongInputLogicException.class)
    public ResponseEntity<Map<String,Object>> wrongInputLogicExceptionHandler(WrongInputLogicException wrongInputLogicException){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",wrongInputLogicException.getMessage());
        exceptionHandlerResponse.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFoundExceptionHandler(NotFoundException notFoundException){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",notFoundException.getMessage());
        exceptionHandlerResponse.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<Map<String,Object>> existsExceptionHandler(ExistsException existsException){
        Map<String,Object> exceptionHandlerResponse = new LinkedHashMap<>();
        exceptionHandlerResponse.put("status",false);
        exceptionHandlerResponse.put("message",existsException.getMessage());
        exceptionHandlerResponse.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        return new ResponseEntity<>(exceptionHandlerResponse,HttpStatus.CONFLICT);
    }
}
