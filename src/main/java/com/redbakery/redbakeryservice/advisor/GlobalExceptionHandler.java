package com.redbakery.redbakeryservice.advisor;

import com.redbakery.redbakeryservice.exception.*;
import com.redbakery.redbakeryservice.common.CommonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.security.SignatureException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse> handleBadeRequestException(Exception e) {
        log.error("Bad Request Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false,e.getMessage(),null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(Exception e) {
        log.error("Not Found Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false,e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CommonResponse> handleCConflictException(Exception e) {
        log.error("Conflict Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CommonResponse> handleForbiddenException(Exception e) {
        log.error("Forbidden Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<CommonResponse> handleInternalServerException(Exception e){
        log.error("Internal Server Error: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CommonResponse> handleUsernameNotFoundException(Exception e){
        log.error("Username Not Found Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Invalid token!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<CommonResponse> handleSignatureException(Exception e){
        log.error("Signature Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Invalid token signature!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CommonResponse> handleJwtException(Exception e){
        log.error("JWT Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Invalid token!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CommonResponse> handleExpiredJwtException(Exception e){
        log.error("Expired JWT Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Your token expired!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> handleAccessDeniedException(Exception e){
        log.error("Access Denied Exception: ", e);
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Your not authorize to access this resource!", null),
                HttpStatus.FORBIDDEN
        );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse> handleMethodArgumentException(MethodArgumentNotValidException exception){
        log.error("Validation Error: ", exception);
        Map<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Validation Error", errorMap),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception e) {
        log.error("Internal Server Error: ", e);
        return new ResponseEntity<>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
