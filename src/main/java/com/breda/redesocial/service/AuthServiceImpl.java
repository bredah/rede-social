package com.breda.redesocial.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.breda.redesocial.exception.UsuarioInexistenteException;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioDetalhesService usuarioDetalhesService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UsuarioDetalhesService usuarioDetalhesService) {
        this.authenticationManager = authenticationManager;
        this.usuarioDetalhesService = usuarioDetalhesService;
    }

    @Override
    public void autenticarUsuario(String apelido, String senha) {
        try {
            // Carregar os detalhes do usuário pelo apelido
            UserDetails userDetails = usuarioDetalhesService.buscarUsuarioPorApelido(apelido);

            // Criar um token de autenticação com as credenciais fornecidas
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails, senha, userDetails.getAuthorities());

            // Autenticar o usuário
            Authentication authentication = authenticationManager.authenticate(token);

            // Verificar se a autenticação foi bem-sucedida
            if (authentication.isAuthenticated()) {
                // O usuário foi autenticado com sucesso
                // Faça o que for necessário aqui
                System.out.println("Usuário autenticado com sucesso: " + apelido);
            } else {
                // A autenticação falhou
                // Lidar com o erro de autenticação
                System.out.println("Falha na autenticação do usuário: " + apelido);
            }
        } catch (UsuarioInexistenteException e) {
            // O usuário não foi encontrado
            // Lidar com o erro de usuário inexistente
            System.out.println("Usuário não encontrado: " + apelido);
        }
    }

    @Override
    public UserDetails buscarUsuarioPorApelido(String apelido) throws UsernameNotFoundException {
        try {
            return usuarioDetalhesService.buscarUsuarioPorApelido(apelido);
        } catch (UsuarioInexistenteException e) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + apelido);
        }
    }
}
