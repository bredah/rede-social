package com.breda.redesocial.controller;

import static io.restassured.RestAssured.given;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import com.breda.redesocial.model.Mensagem;
import com.breda.redesocial.repository.MensagemRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class MensagemControllerIT {
  @LocalServerPort
  private int port;

  @Autowired
  private MensagemRepository mensagemRepository;

  private Mensagem mensagem;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    mensagem = null;
  }

  @AfterEach
  public void cleanup() {
    if (mensagem != null) {
      mensagemRepository.delete(mensagem);
    }
  }

  @Test
  @Order(1)
  public void criarMensagem_deveRetornarStatusCreated() {
    given()
        .contentType(ContentType.JSON)
        .body("{ \"usuario\": \"joao\", \"conteudo\": \"Olá!\" }")
        .when()
        .post("/mensagens")
        .then()
        .statusCode(201);
  }

  @Test
  public void buscarMensagem_deveRetornarStatusOk() {
    registrarMensagem();
    given()
        .pathParam("id", mensagem.getId())
        .when()
        .get("/mensagens/{id}")
        .then()
        .statusCode(200);
  }

  @Test
  public void incrementarGostei_deveRetornarStatusOk() {
    registrarMensagem();
    given()
        .pathParam("id", mensagem.getId())
        .when()
        .put("/mensagens/{id}/gostei")
        .then()
        .statusCode(200);
  }

  @Test
  public void incrementarNaoGostei_deveRetornarStatusOk() {
    registrarMensagem();
    given()
        .pathParam("id", mensagem.getId())
        .when()
        .put("/mensagens/{id}/naogostei")
        .then()
        .statusCode(200);
  }

  @Test
  public void excluirMensagem_deveRetornarStatusOk() {
    registrarMensagem();
    given()
        .pathParam("id", mensagem.getId())
        .when()
        .delete("/mensagens/{id}")
        .then()
        .statusCode(200);
  }

  @Test
  public void listarMensagens_deveRetornarStatusOk() {
    registrarMensagem();
    given()
        .param("page", 0)
        .param("size", 10)
        .when()
        .get("/mensagens")
        .then()
        .statusCode(200);
  }

  private void registrarMensagem() {
    this.mensagem = Mensagem.builder()
        .usuario("Usuário")
        .conteudo("Conteúdo da mensagem")
        .id(UUID.randomUUID())
        .build();
    mensagemRepository.save(mensagem);
  }

}
