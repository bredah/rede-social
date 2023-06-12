package com.breda.redesocial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breda.redesocial.model.Usuario;
import com.breda.redesocial.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    public void testCadastrarUsuario() {
        var usuario = criarUsuario();

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        usuarioService.cadastrarUsuario(usuario);

        verify(usuarioRepository, times(1)).save(usuario);

    }

    @Test
    public void testTrocarSenhaUsuario() {
        UUID id = UUID.randomUUID();
        String novaSenha = "4321dcbA";

        var usuario = criarUsuario();

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        usuarioService.trocarSenhaUsuario(usuario.getId(), novaSenha);

        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(usuarioRepository, times(1)).save(usuario);

        assertThat(usuario.getSenha()).isEqualTo(novaSenha);
    }

    @Test
    public void testConsultarUsuarioPorId() {
        UUID id = UUID.randomUUID();

        var usuario = criarUsuario();

        Usuario resultado = usuarioService.consultarUsuarioPorId(id);

        verify(usuarioRepository, times(1)).findById(id);

        assertThat(resultado).isEqualTo(usuario);
    }

    @Test
    public void testDeletarUsuario() {
        UUID id = UUID.randomUUID();

        var usuario = criarUsuario();

        // Chamada do método deletarUsuario
        usuarioService.excluirUsuario(id);

        // Verificação se o método deleteById foi chamado com o ID correto
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    private Usuario criarUsuario() {
        // Criação de um usuário para teste
        return Usuario.builder()
                .id(UUID.randomUUID())
                .apelido("john.doe")
                .email("john.doe@example.com")
                .nome("John Doe")
                .senha("Abce1234")
                .bloqueado(false)
                .role("USER")
                .build();
    }

}
