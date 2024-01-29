package com.redbakery.redbakeryservice.advisor;

import com.redbakery.redbakeryservice.exception.*;
import com.redbakery.redbakeryservice.common.CommonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CommonResponse> handleUsernameNotFoundException(Exception e){
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Invalid token!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<CommonResponse> handleSignatureException(Exception e){
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Invalid token signature!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CommonResponse> handleJwtException(Exception e){
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Invalid token!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CommonResponse> handleExpiredJwtException(Exception e){
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Your token expired!", null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> handleAccessDeniedException(Exception e){
        return new ResponseEntity<CommonResponse>(
                new CommonResponse(false, "Your not authorize to access this resource!", null),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                new CommonResponse(false, e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
