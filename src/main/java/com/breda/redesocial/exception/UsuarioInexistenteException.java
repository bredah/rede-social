package com.breda.redesocial.exception;

public class UsuarioInexistenteException extends RuntimeException {

  public UsuarioInexistenteException(String message) {
    super(message);
  }

  public UsuarioInexistenteException(String message, Throwable cause) {
    super(message, cause);
  }
}
