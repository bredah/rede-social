package com.breda.redesocial.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid4")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario;
  // @NotEmpty(message = "usuario não pode estar vazio")
  // private String usuario;

  @Column(nullable = false)
  @NotEmpty(message = "conteudo não pode estar vazio")
  private String conteudo;

  @Default
  private LocalDateTime dataCriacao = LocalDateTime.now();

  @Default
  private int gostei = 0;

  @Default
  private int naoGostei = 0;

}
