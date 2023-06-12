package com.breda.redesocial.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.breda.redesocial.model.Usuario;
import com.breda.redesocial.service.UsuarioService;


public class UsuarioControllerTest {

  @Mock
  private UsuarioService usuarioService;

  @InjectMocks
  private UsuarioController usuarioController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCadastrarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setApelido("John Doe");
    usuario.setSenha("123456");

    ResponseEntity<?> response = usuarioController.cadastrarUsuario(usuario);

    verify(usuarioService, times(1)).cadastrarUsuario(any(Usuario.class));
    verifyNoMoreInteractions(usuarioService);

    // Verificar asserções no ResponseEntity e/ou retornos esperados
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEqualTo("Usuário cadastrado com sucesso.");
  }

  @Test
  public void testConsultarUsuarioPorId() {
    UUID id = UUID.randomUUID();
    Usuario usuario = new Usuario();
    usuario.setId(id);
    usuario.setApelido("John Doe");
    usuario.setSenha("123456");

    when(usuarioService.consultarUsuarioPorId(eq(id))).thenReturn(usuario);

    ResponseEntity<?> response = usuarioController.consultarUsuarioPorId(id);

    verify(usuarioService, times(1)).consultarUsuarioPorId(eq(id));
    verifyNoMoreInteractions(usuarioService);

    // Verificar asserções no ResponseEntity e/ou retornos esperados
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(usuario);
  }

  @Test
  public void testTrocarSenhaUsuario() {
    UUID id = UUID.randomUUID();
    String novaSenha = "654321";

    when(usuarioService.trocarSenhaUsuario(eq(id), eq(novaSenha))).thenReturn(true);

    ResponseEntity<?> response = usuarioController.trocarSenhaUsuario(id, novaSenha);

    verify(usuarioService, times(1)).trocarSenhaUsuario(eq(id), anyString());
    verifyNoMoreInteractions(usuarioService);

    // Verificar asserções no ResponseEntity e/ou retornos esperados
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testExcluirUsuario() {
    UUID id = UUID.randomUUID();

    doNothing().when(usuarioService).excluirUsuario(eq(id));

    ResponseEntity<?> response = usuarioController.excluirUsuario(id);

    verify(usuarioService, times(1)).excluirUsuario(eq(id));
    verifyNoMoreInteractions(usuarioService);

    // Verificar asserções no ResponseEntity e/ou retornos esperados
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEqualTo("Usuário excluído com sucesso.");
  }

  @Test
  public void testBloquearUsuario() {
    UUID id = UUID.randomUUID();

    when(usuarioService.bloquearUsuario(eq(id))).thenReturn(true);

    ResponseEntity<?> response = usuarioController.bloquearUsuario(id);

    verify(usuarioService, times(1)).bloquearUsuario(eq(id));
    verifyNoMoreInteractions(usuarioService);

    // Verificar asserções no ResponseEntity e/ou retornos esperados
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEqualTo("Usuário bloqueado com sucesso.");
  }
}
