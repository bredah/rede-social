package com.breda.redesocial.exception;

public class UsuarioExistenteException extends RuntimeException {

  public UsuarioExistenteException(String message) {
    super(message);
  }

  public UsuarioExistenteException(String message, Throwable cause) {
    super(message, cause);
  }
}
