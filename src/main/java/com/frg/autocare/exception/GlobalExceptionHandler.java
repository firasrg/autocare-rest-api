/**
 * AutoCare REST API - Global exception handler.
 * Copyright (C) 2024  AutoCare REST API original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this application.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.frg.autocare.exception;

import java.util.*;
import java.util.stream.Collectors;

import com.frg.autocare.enums.CarSortField;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(
      BadCredentialsException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.UNAUTHORIZED, request);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchElementException(
      NoSuchElementException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ValidationErrorResponse errorResponse =
        new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation error",
            request.getDescription(false),
            errors);

    log.error("Validation error: {}", errors);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
    log.error("Unhandled exception", ex);
    return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  private ResponseEntity<ErrorResponse> createErrorResponse(
      Exception ex, HttpStatus status, WebRequest request) {
    ErrorResponse errorResponse =
        new ErrorResponse(status.value(), ex.getMessage(), request.getDescription(false));

    log.error("{} error: {}", status, ex.getMessage());
    return new ResponseEntity<>(errorResponse, status);
  }
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    String paramName = ex.getName();
    String invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null";

    String message;

    if ("sortBy".equals(paramName)) {
      String validValues = Arrays.stream(CarSortField.values())
              .map(Enum::name)
              .collect(Collectors.joining(", "));
      message = String.format(
              "Invalid value '%s' for parameter '%s'. Allowed values are: [%s].",
              invalidValue, paramName, validValues
      );
    } else if ("sortDir".equals(paramName)) {
      String validValues = Arrays.stream(Sort.Direction.values())
              .map(Enum::name)
              .collect(Collectors.joining(", "));
      message = String.format(
              "Invalid value '%s' for parameter '%s'. Allowed values are: [%s].",
              invalidValue, paramName, validValues
      );
    } else {
      message = String.format("Invalid value '%s' for parameter '%s'.", invalidValue, paramName);
    }

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("message", message);
    body.put("path", request.getRequestURI());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
          ConstraintViolationException ex, HttpServletRequest request) {

    Map<String, String> violations = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                    v -> v.getPropertyPath().toString(),
                    v -> v.getMessage(),
                    (msg1, msg2) -> msg1
            ));

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("message", "Validation error");
    body.put("path", request.getRequestURI());
    body.put("errors", violations);

    log.error("Constraint violation: {}", violations);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}
