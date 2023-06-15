package com.breda.redesocial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.breda.redesocial.model.Role;
import com.breda.redesocial.model.Usuario;

@DataJpaTest
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioRepositoryIT {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Test
  @Order(1)
  public void devePermitirCriarTabela() {
    long totalTabelasCriada = usuarioRepository.count();
    assertThat(totalTabelasCriada).isGreaterThanOrEqualTo(0);
  }

  @Test
  @Order(2)
  public void devePermitirRegistrarMensagem() {
    var usuario = criarRegistro();
    assertThat(usuario.getId()).isNotNull();
  }

  @Test
  @Order(3)
  public void devePermitirLerMensagem() {
    // registrar usuario
    var usuario = criarRegistro();
    // verificar se a mensagem existente
    Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuario.getId());
    assertThat(usuarioEncontrado).isPresent();
    assertThat(usuarioEncontrado.get()).isEqualTo(usuario);
  }

  @Test
  @Order(3)
  public void devePermitirAlterarMensagem() {
    // registrar usuario
    var usuario = criarRegistro();
    // atualizar usuario
    Usuario usuarioAtualizado = usuario;
    usuarioAtualizado.setNome("Batatinha");
    // verificar se a atualização foi efetuada
    Usuario usuarioAtualizadoEncontrado = usuarioRepository.save(usuarioAtualizado);
    assertThat(usuarioAtualizadoEncontrado).isEqualTo(usuarioAtualizado);
  }

  @Test
  @Order(3)
  public void devePermitirRemoverMensagem() {
    // registrar usuario
    var usuario = criarRegistro();
    // verificar se o usuario foi removido
    usuarioRepository.delete(usuario);
    assertThat(usuarioRepository.findById(usuario.getId())).isEmpty();
  }

  private Usuario criarUsuario() {
    // Criação de um usuário para teste
    return Usuario.builder()
        .apelido("john.doe")
        .email("john.doe@example.com")
        .nome("John Doe")
        .senha("Abce1234")
        .bloqueado(false)
        .role(Role.USER)
        .build();
  }

  private Usuario criarRegistro() {
    var usuario = criarUsuario();
    return usuarioRepository.save(usuario);
  }

}
