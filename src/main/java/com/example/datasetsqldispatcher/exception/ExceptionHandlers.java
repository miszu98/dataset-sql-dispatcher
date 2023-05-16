package com.example.datasetsqldispatcher.exception;

import com.example.datasetsqldispatcher.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ExceptionHandlers {

    private final static DateTimeFormatter ERROR_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(BadFileTypeException.class)
    public ResponseEntity<ExceptionResponse> handleBadFileTypeException(BadFileTypeException exception) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(createExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    private ExceptionResponse createExceptionResponse(String message, HttpStatus errorCode) {
        return  ExceptionResponse.builder()
                .message(message)
                .errorCode(errorCode)
                .errorTime(LocalDateTime.now().format(ERROR_DATE_TIME_PATTERN))
                .build();
    }

}
