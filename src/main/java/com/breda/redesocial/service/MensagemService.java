package com.breda.redesocial.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breda.redesocial.model.Mensagem;

public interface MensagemService {

  Mensagem criarMensagem(Mensagem mensagem);

  Mensagem buscarMensagem(UUID mensagemId);

  void incrementarGostei(UUID mensagemId);

  void incrementarNaoGostei(UUID mensagemId);

  Page<Mensagem> obterTodasAsMensagensEmOrdemDescrescente(Pageable pageable);

  void removerMensagem(UUID mensagemId);

}
