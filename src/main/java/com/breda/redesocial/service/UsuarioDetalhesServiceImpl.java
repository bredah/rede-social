package com.breda.redesocial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.breda.redesocial.exception.UsuarioInexistenteException;
import com.breda.redesocial.model.Usuario;
import com.breda.redesocial.repository.UsuarioRepository;

@Service
public class UsuarioDetalhesServiceImpl implements UsuarioDetalhesService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails buscarUsuarioPorApelido(String apelido) throws UsuarioInexistenteException {
        Usuario usuario = usuarioRepository.findByApelido(apelido)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuário não encontrado: " + apelido));

        List<GrantedAuthority> permissoes = new ArrayList<>();
        permissoes.add(new SimpleGrantedAuthority("ROLE_USER")); // Defina as permissões do usuário conforme necessário

        return new org.springframework.security.core.userdetails.User(
                usuario.getApelido(),
                usuario.getSenha(),
                permissoes);
    }
}
