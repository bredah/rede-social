package com.breda.redesocial.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.breda.redesocial.model.Usuario;
import com.breda.redesocial.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

  private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);


  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  // Cadastro de usuário
  @PostMapping()
  public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid Usuario usuario) {
    // logger.info("cadastrando novo usuario");
    // usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    usuario.setId(UUID.randomUUID());
    usuarioService.cadastrarUsuario(usuario);
    return new ResponseEntity<>("Usuário cadastrado com sucesso.", HttpStatus.CREATED);
  }

  // Consulta de usuário por ID
  @GetMapping("/{id}")
  public ResponseEntity<Usuario> consultarUsuarioPorId(@PathVariable("id") UUID id) {
    Usuario usuario = usuarioService.consultarUsuarioPorId(id);
    if (usuario != null) {
      return new ResponseEntity<>(usuario, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Troca de senha de usuário
  @PutMapping("/{id}/trocar-senha")
  public ResponseEntity<?> trocarSenhaUsuario(@PathVariable("id") UUID id,
      @RequestBody String novaSenha) {
    boolean sucesso = usuarioService.trocarSenhaUsuario(id, novaSenha);
    if (sucesso) {
      return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Falha ao alterar senha.", HttpStatus.BAD_REQUEST);
    }
  }

  // Exclusão de usuário
  @DeleteMapping("/{id}")
  public ResponseEntity<?> excluirUsuario(@PathVariable("id") UUID id) {
    usuarioService.excluirUsuario(id);
    return new ResponseEntity<>("Usuário excluído com sucesso.", HttpStatus.OK);
  }

  // Bloqueio de usuário
  @PutMapping("/{id}/bloquear")
  public ResponseEntity<?> bloquearUsuario(@PathVariable("id") UUID id) {
    boolean sucesso = usuarioService.bloquearUsuario(id);
    if (sucesso) {
      return new ResponseEntity<>("Usuário bloqueado com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Falha ao bloquear usuário.", HttpStatus.BAD_REQUEST);
    }
  }

  // Desbloqueio de usuário
  @PutMapping("/{id}/desbloquear")
  public ResponseEntity<?> desbloquearUsuario(@PathVariable("id") UUID id) {
    boolean sucesso = usuarioService.desbloquearUsuario(id);
    if (sucesso) {
      return new ResponseEntity<>("Usuário desbloqueado com sucesso.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Falha ao desbloquear usuário.", HttpStatus.BAD_REQUEST);
    }
  }
}
