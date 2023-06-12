package com.breda.redesocial.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.breda.redesocial.exception.MensagemNotFoundException;
import com.breda.redesocial.model.Mensagem;
import com.breda.redesocial.repository.MensagemRepository;

@Service
public class MensagemServiceImpl implements MensagemService {

  @Autowired
  private MensagemRepository mensagemRepository;

  @Override
  public Mensagem criarMensagem(Mensagem mensagem) {
    mensagem.setId(UUID.randomUUID());
    return mensagemRepository.save(mensagem);
  }

  @Override
  public Mensagem buscarMensagem(UUID mensagemId) {
    return mensagemRepository.findById(mensagemId)
        .orElseThrow(() -> new MensagemNotFoundException("Mensagem não encontrada"));
  }

  @Override
  public void incrementarGostei(UUID mensagemId) {
    var mensagem = buscarMensagem(mensagemId);
    mensagem.setGostei(mensagem.getGostei() + 1);
    mensagemRepository.save(mensagem);
  }

  @Override
  public void incrementarNaoGostei(UUID mensagemId) {
    var mensagem = buscarMensagem(mensagemId);
    mensagem.setNaoGostei(mensagem.getNaoGostei() + 1);
    mensagemRepository.save(mensagem);
  }

  @Override
  public Page<Mensagem> obterTodasAsMensagensEmOrdemDescrescente(Pageable pageable) {
    return mensagemRepository.obterTodasAsMensagensEmOrdemDescrescente(pageable);
  }

  @Override
  public void removerMensagem(UUID mensagemId) {
    mensagemRepository.deleteById(mensagemId);
  }

  public Optional<Mensagem> existeMensagem(UUID mensagemId) {
    return mensagemRepository.findById(mensagemId);
  }

}
