package com.cloud.spring.tp1.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail exception(Exception e) {

        log.error(e.getMessage(), e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED,"Please try later");
    }
}
