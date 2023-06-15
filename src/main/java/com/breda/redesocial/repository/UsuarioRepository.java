package com.breda.redesocial.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breda.redesocial.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  Optional<Usuario> findByApelido(String apelido);
}
