package com.breda.redesocial.exception;

public class ImagemNotFoundException extends RuntimeException {

  public ImagemNotFoundException(String mensagem) {
    super(mensagem);
  }

  public ImagemNotFoundException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }
}
