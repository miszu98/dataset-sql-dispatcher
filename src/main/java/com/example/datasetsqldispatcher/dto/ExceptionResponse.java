package com.example.datasetsqldispatcher.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public record ExceptionResponse(String errorTime, String message, HttpStatus errorCode) {
    @Builder  public ExceptionResponse {}
}
