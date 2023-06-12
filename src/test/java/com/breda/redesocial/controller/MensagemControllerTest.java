package com.breda.redesocial.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.breda.redesocial.dto.MensagemResponse;
import com.breda.redesocial.model.Mensagem;
import com.breda.redesocial.service.MensagemService;

class MensagemControllerTest {

  @InjectMocks
  private MensagemController mensagemController;

  @Mock
  private MensagemService mensagemService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void criarMensagem_deveRetornarMensagemCriadaComSucesso() {
    // Arrange
    Mensagem mensagem = criarMensagem();
    Mensagem mensagemCriada = criarMensagem();
    mensagemCriada.setId(UUID.randomUUID());

    when(mensagemService.criarMensagem(any(Mensagem.class))).thenReturn(mensagemCriada);

    // Act
    ResponseEntity<?> responseEntity = mensagemController.criarMensagem(mensagem);

    // Assert
    verify(mensagemService, atLeastOnce()).criarMensagem(mensagem);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    MensagemResponse responseBody = (MensagemResponse) responseEntity.getBody();
    assertThat(responseBody).isNotNull();
    assertThat(responseBody.getMensagem()).isEqualTo("Mensagem criada com sucesso");
    assertThat(responseBody.getId()).isEqualTo(mensagemCriada.getId());
  }

  @Test
  void buscarMensagem_deveRetornarMensagemEncontrada() {
    // Arrange
    UUID id = UUID.randomUUID();
    Mensagem mensagemEncontrada = criarMensagem();
    mensagemEncontrada.setId(id);

    when(mensagemService.buscarMensagem(id)).thenReturn(mensagemEncontrada);

    // Act
    ResponseEntity<?> responseEntity = mensagemController.buscarMensagem(id);

    // Assert
    verify(mensagemService, atLeastOnce()).buscarMensagem(id);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    Mensagem mensagemResponse = (Mensagem) responseEntity.getBody();
    assertThat(mensagemResponse).isNotNull();
    assertThat(mensagemResponse.getId()).isEqualTo(mensagemEncontrada.getId());
    assertThat(mensagemResponse.getUsuario()).isEqualTo(mensagemEncontrada.getUsuario());
    assertThat(mensagemResponse.getConteudo()).isEqualTo(mensagemEncontrada.getConteudo());

  }

  @Test
  void incrementarGostei_deveRetornarContadorIncrementadoComSucesso() {
    // Arrange
    UUID id = UUID.randomUUID();

    // Act
    ResponseEntity<?> responseEntity = mensagemController.incrementarGostei(id);

    // Assert
    verify(mensagemService, atLeastOnce()).incrementarGostei(id);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    MensagemResponse responseBody = (MensagemResponse) responseEntity.getBody();
    assertThat(responseBody).isNotNull();
    assertThat(responseBody.getMensagem()).isEqualTo("Contador de 'gostei' incrementado com sucesso.");
    assertThat(responseBody.getId()).isEqualTo(id);
  }

  @Test
  void incrementarNaoGostei_deveRetornarContadorIncrementadoComSucesso() {
    // Arrange
    UUID id = UUID.randomUUID();

    // Act
    ResponseEntity<?> responseEntity = mensagemController.incrementarNaoGostei(id);

    // Assert
    verify(mensagemService, atLeastOnce()).incrementarNaoGostei(id);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    MensagemResponse responseBody = (MensagemResponse) responseEntity.getBody();
    assertThat(responseBody).isNotNull();
    assertThat(responseBody.getMensagem()).isEqualTo("Contador de 'não gostei' incrementado com sucesso.");
    assertThat(responseBody.getId()).isEqualTo(id);
  }

  @Test
  void excluirMensagem_deveRetornarMensagemExcluidaComSucesso() {
    // Arrange
    UUID id = UUID.randomUUID();

    // Act
    ResponseEntity<?> responseEntity = mensagemController.excluirMensagem(id);

    // Assert
    verify(mensagemService, atLeastOnce()).removerMensagem(id);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    MensagemResponse responseBody = (MensagemResponse) responseEntity.getBody();
    assertThat(responseBody).isNotNull();
    assertThat(responseBody.getMensagem()).isEqualTo("Mensagem excluída com sucesso.");
    assertThat(responseBody.getId()).isEqualTo(id);
  }

  @Test
  void listarMensagens_deveRetornarListaPaginadaDeMensagens() {
    // Arrange
    int page = 0;
    int size = 10;
    Pageable pageable = PageRequest.of(page, size);
    Page<Mensagem> mensagens = criarMensagensPage();

    when(mensagemService.obterTodasAsMensagensEmOrdemDescrescente(pageable)).thenReturn(mensagens);

    // Act
    ResponseEntity<Page<Mensagem>> responseEntity = mensagemController.listarMensagens(page, size);

    // Assert
    verify(mensagemService, atLeastOnce()).obterTodasAsMensagensEmOrdemDescrescente(pageable);
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    Page<Mensagem> responseBody = responseEntity.getBody();
    assertThat(responseBody).isNotNull();
    assertThat(responseBody.getContent()).isEqualTo(mensagens.getContent());
    assertThat(responseBody.getTotalElements()).isEqualTo(mensagens.getTotalElements());
  }

  private Mensagem criarMensagem() {
    Mensagem mensagem = new Mensagem();
    mensagem.setUsuario("Usuário");
    mensagem.setConteudo("Conteúdo da mensagem");
    return mensagem;
  }

  private Page<Mensagem> criarMensagensPage() {
    List<Mensagem> mensagens = new ArrayList<>();
    mensagens.add(criarMensagem());
    return new PageImpl<>(mensagens);
  }
}
