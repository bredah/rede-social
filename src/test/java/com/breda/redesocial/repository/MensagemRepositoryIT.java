package com.breda.redesocial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.breda.redesocial.helper.DisplayTestName;
import com.breda.redesocial.model.Mensagem;

@DataJpaTest
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(DisplayTestName.class)
class MensagemRepositoryIT {

  @Autowired
  private MensagemRepository mensagemRepository;

  @Test
  @Tag("slow")
  @Order(1)
  public void devePermitirCriarTabela() {
    long totalTabelasCriada = mensagemRepository.count();
    assertThat(totalTabelasCriada).isGreaterThanOrEqualTo(0);
  }

  @Test
  @Tag("integration")
  @Order(2)
  public void devePermitirRegistrarMensagem() {
    Mensagem savedMensagem = criarRegistro();
    assertThat(savedMensagem.getId()).isNotNull();
  }

  @Test
  @Order(3)
  public void devePermitirLerMensagem() {
    // Regitrar mensagem
    Mensagem savedMensagem = criarRegistro();

    // Verificar se a mensagem existe
    Mensagem retrievedMensagem = mensagemRepository.findById(savedMensagem.getId()).orElse(null);
    assertThat(retrievedMensagem).isNotNull();
    assertThat(retrievedMensagem.getId()).isEqualTo(savedMensagem.getId());
  }

  @Test
  @Order(4)
  public void devePermitirAlterarMensagem() {
    // Regitrar mensagem
    Mensagem savedMensagem = criarRegistro();

    // Atualizar mensagem
    Mensagem updatedMensagem = savedMensagem;
    updatedMensagem.setConteudo("Conteúdo alterado da mensagem de teste");

    // Verificar se a atualização foi efetuada
    Mensagem savedUpdatedMensagem = mensagemRepository.save(updatedMensagem);
    assertThat(savedUpdatedMensagem.getConteudo()).isEqualTo(updatedMensagem.getConteudo());
  }

  @Test
  @Order(5)
  public void devePermitirRemoverMensagem() {
    // Regitrar mensagem
    Mensagem savedMensagem = criarRegistro();

    // Verificar se a mensagem foi removida
    mensagemRepository.delete(savedMensagem);
    Mensagem retrievedMensagem = mensagemRepository.findById(savedMensagem.getId()).orElse(null);
    assertThat(retrievedMensagem).isNull();
  }

  @Test
  @Order(6)
  public void deveRetornarMensagensEmOrdemDescrescente() {
    // Adicionar algumas mensagens
    criarRegistro();
    criarRegistro();
    criarRegistro();

    // Obter as mensagens em ordem decrescente
    PageRequest pageable = PageRequest.of(0, 10);
    Page<Mensagem> mensagens = mensagemRepository.obterTodasAsMensagensEmOrdemDescrescente(pageable);
    List<Mensagem> mensagemList = mensagens.getContent();

    // Verificar se as mensagens foram retornadas em ordem decrescente
    for (int i = 0; i < mensagemList.size() - 1; i++) {
      Mensagem mensagemAtual = mensagemList.get(i);
      Mensagem mensagemSeguinte = mensagemList.get(i + 1);
      assertThat(mensagemAtual.getDataCriacao()).isAfterOrEqualTo(mensagemSeguinte.getDataCriacao());
    }
  }

  private Mensagem criarMensagem() {
    return Mensagem.builder()
        .usuario("Usuário Teste")
        .conteudo("Conteúdo da mensagem de teste")
        .id(UUID.randomUUID())
        .build();
  }

  private Mensagem criarRegistro() {
    var mensagem = criarMensagem();
    return mensagemRepository.save(mensagem);
  }

}
