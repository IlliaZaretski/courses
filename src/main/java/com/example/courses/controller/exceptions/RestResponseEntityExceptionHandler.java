package com.example.courses.controller.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({EntityNotFoundException.class,
            EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            InvalidBookingRequestException.class})
    @ResponseStatus(BAD_REQUEST)
    String requestValidationError(Exception ex) {
        return "Request is invalid: " + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(BAD_REQUEST)
    String requestValidationError(ConstraintViolationException ex) {
        ConstraintViolation violation = ex.getConstraintViolations().stream().findFirst().get();
        return "Property '" + violation.getPropertyPath() + "' " + violation.getMessage() + ": " + violation.getInvalidValue();
    }

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleException(RuntimeException ex) {
        return "Unknown error: " + ex.getMessage();
    }

}