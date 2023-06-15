package com.breda.redesocial.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.breda.redesocial.model.Mensagem;


public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {
  @Query("SELECT m FROM Mensagem m ORDER BY m.dataCriacao DESC")
  Page<Mensagem> obterTodasAsMensagensEmOrdemDescrescente(Pageable pageable);
}
