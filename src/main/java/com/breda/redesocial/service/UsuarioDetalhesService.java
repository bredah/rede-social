package com.breda.redesocial.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.breda.redesocial.exception.UsuarioInexistenteException;

public interface UsuarioDetalhesService {
  
  UserDetails buscarUsuarioPorApelido(String apelido) throws UsuarioInexistenteException;

}
