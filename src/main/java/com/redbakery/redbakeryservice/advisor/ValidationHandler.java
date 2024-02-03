package com.redbakery.redbakeryservice.advisor;

import com.redbakery.redbakeryservice.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationHandler {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<CommonResponse> handleMethodArgumentException(MethodArgumentNotValidException exception){
//        Map<String, String> errorMap = new HashMap<>();
//
//        exception.getBindingResult().getFieldErrors().forEach(error -> {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//
//        return new ResponseEntity<CommonResponse>(
//                new CommonResponse(false, "Validation Error", errorMap),
//                HttpStatus.BAD_REQUEST
//        );
//    }
}
