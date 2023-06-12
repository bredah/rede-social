package com.breda.redesocial.exception;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

public class ValidationException extends RuntimeException {

  private final Set<ConstraintViolation<?>> violations;

  public ValidationException(String message, Set<ConstraintViolation<?>> violations) {
    super(message);
    this.violations = violations;
  }

  public Set<ConstraintViolation<?>> getViolations() {
    return violations;
  }
}
