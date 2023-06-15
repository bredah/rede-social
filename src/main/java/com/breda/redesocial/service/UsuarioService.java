package com.breda.redesocial.service;

import java.util.UUID;

import com.breda.redesocial.model.Usuario;

public interface UsuarioService {

  void cadastrarUsuario(Usuario usuario);

  Usuario consultarUsuarioPorId(UUID id);

  Usuario consultarUsuarioPorApelido(String apelido);

  boolean trocarSenhaUsuario(UUID id, String novaSenha);

  void excluirUsuario(UUID id);

  boolean bloquearUsuario(UUID id);

  boolean desbloquearUsuario(UUID id);
}
