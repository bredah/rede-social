package com.breda.redesocial.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
  void autenticarUsuario(String apelido, String senha);
  UserDetails buscarUsuarioPorApelido(String apelido) throws UsernameNotFoundException;
}
