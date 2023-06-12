package com.breda.redesocial.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.breda.redesocial.dto.ErrorResponse;
import com.breda.redesocial.exception.UsuarioExistenteException;
import com.breda.redesocial.exception.ValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UsuarioExistenteException.class)
  public ResponseEntity<String> handleUsuarioExistenteException(UsuarioExistenteException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      // String fieldName = ((FieldError) error).getField();
      // String errorMessage = error.getDefaultMessage();
      // errors.add(String.format("campo: %s - erro: %s", fieldName, errorMessage));
      errors.add(error.getDefaultMessage());
    }
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }


  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
    List<String> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : ex.getViolations()) {
      errors.add(violation.getMessage());
    }
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex) {
    List<String> errors = new ArrayList<>();
    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
    for (ConstraintViolation<?> violation : constraintViolations) {
      errors.add(violation.getMessage());
    }
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }


  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(
      DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      ConstraintViolationException constraintViolationException =
          (ConstraintViolationException) ex.getCause();
      Set<ConstraintViolation<?>> violations =
          constraintViolationException.getConstraintViolations();
      List<String> messages = new ArrayList<>();
      for (ConstraintViolation<?> violation : violations) {
        messages.add(violation.getMessage());
      }
      String errorMessage = "Validation failed: " + String.join(", ", messages);
      return ResponseEntity.badRequest().body(errorMessage);
    }
    // Handle other exceptions if needed
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  
}
