package com.example.courses.classic.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
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
    @ExceptionHandler(BookingAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    String requestValidationError(BookingAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(BAD_REQUEST)
    String requestValidationError(ConstraintViolationException ex) {
        final StringBuilder sb = new StringBuilder().append("Violations: \n");
        ex.getConstraintViolations().forEach(violation -> sb.append("Property '").append(violation.getPropertyPath()).append("' ")
                .append(violation.getMessage()).append(": ").append(violation.getInvalidValue()).append(" \n"));
        return sb.toString();
    }

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleException(RuntimeException ex) {
        return "Unknown error: " + ex.getMessage();
    }

}