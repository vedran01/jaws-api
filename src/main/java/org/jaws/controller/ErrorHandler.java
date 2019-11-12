package org.jaws.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jaws.core.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<?> handeResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    logger.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorBody(e, HttpStatus.NOT_FOUND, request));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    Map<String, String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    logger.error("Invalid request body for " + StringUtils.capitalize(ex.getBindingResult().getObjectName()));
    return ResponseEntity.status(status).body(getErrorBody("Invalid request body", status, request, errors));
  }

  private String getErrorBody(Object... args) {
    Map<String, Object> errors = new LinkedHashMap<>();

    for (Object o : args) {
      if (o instanceof String) {
        errors.put("message", o);
      } else if (o instanceof Throwable) {
        errors.put("message", ((Throwable) o).getMessage());
      } else if (o instanceof HttpStatus) {
        errors.put("status", o);
        errors.put("code", ((HttpStatus) o).value());
      } else if (o instanceof HttpServletRequest) {
        errors.put("path", ((HttpServletRequest) o).getRequestURI());
      } else if (o instanceof WebRequest) {
        errors.put("path", ((ServletWebRequest) o).getRequest().getRequestURI());
      } else if (o instanceof Collection) {
        errors.put("errors", o);
      } else if (o instanceof Map) {
        errors.put("error", o);
      }
    }

    try {

      return objectMapper.writeValueAsString(errors);

    } catch (JsonProcessingException e) {
      logger.error("Error parsing error response", e);
      return "{\"message\":\"Something wrong with error parser\"}";
    }
  }

}
