package com.breda.redesocial.exception;

public class MensagemNotFoundException extends RuntimeException {

  public MensagemNotFoundException(String mensagem) {
    super(mensagem);
  }

  public MensagemNotFoundException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }
}
