package com.breda.redesocial.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breda.redesocial.dto.MensagemRequest;
import com.breda.redesocial.dto.MensagemResponse;
import com.breda.redesocial.model.Mensagem;
import com.breda.redesocial.service.MensagemService;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

  private static final Logger logger = LoggerFactory.getLogger(MensagemController.class);

  @Autowired
  private MensagemService mensagemService;

  @PostMapping()
  public ResponseEntity<?> criarMensagem(@RequestBody MensagemRequest mensagemRequest) {
    var mensagemCriada = mensagemService.criarMensagem(mensagemRequest);
    var response = new MensagemResponse("Mensagem criada com sucesso", mensagemCriada.getId());
    logger.info("mensagem adicionada: ID={}", mensagemCriada.getId());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarMensagem(@PathVariable UUID id) {
    var mensagem = mensagemService.buscarMensagem(id);
    logger.info("Mensagem encontrada: ID={}", mensagem.getId());
    return new ResponseEntity<>(mensagem, HttpStatus.OK);
  }

  @PutMapping("/{id}/gostei")
  public ResponseEntity<?> incrementarGostei(@PathVariable UUID id) {
    mensagemService.incrementarGostei(id);
    var response = new MensagemResponse("Contador de 'gostei' incrementado com sucesso.", id);
    logger.info("Contador de 'gostei' incrementado para a mensagem de ID={}", id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{id}/naogostei")
  public ResponseEntity<?> incrementarNaoGostei(@PathVariable UUID id) {
    mensagemService.incrementarNaoGostei(id);
    var response = new MensagemResponse("Contador de 'não gostei' incrementado com sucesso.", id);
    logger.info("Contador de 'não gostei' incrementado para a mensagem de ID={}", id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> excluirMensagem(@PathVariable("id") UUID id) {
    mensagemService.removerMensagem(id);
    var response = new MensagemResponse("Mensagem excluída com sucesso.", id);
    logger.info("Mensagem excluída: ID={}", id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<Page<Mensagem>> listarMensagens(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Mensagem> mensagens = mensagemService.obterTodasAsMensagensEmOrdemDescrescente(pageable);
    logger.info("Listagem de mensagens: Total={}, Página={}, Tamanho={}", mensagens.getTotalElements(), page, size);
    return ResponseEntity.ok(mensagens);
  }

}
