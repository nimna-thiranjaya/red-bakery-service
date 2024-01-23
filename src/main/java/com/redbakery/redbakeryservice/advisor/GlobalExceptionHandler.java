package com.redbakery.redbakeryservice.advisor;

import com.redbakery.redbakeryservice.exception.*;
import com.redbakery.redbakeryservice.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse> handleBadeRequestException(Exception e) {
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false,e.getMessage(),null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(Exception e) {
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false,e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CommonResponse> handleCConflictException(Exception e) {
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CommonResponse> handleForbiddenException(Exception e) {
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<CommonResponse> handleInternalServerException(Exception e){
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
