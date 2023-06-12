package com.breda.redesocial.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

  
  @Override
  public void run(String... args) throws Exception {
    return;
    // var total = 20;
    // for (int i = 0; i < total; i++) {
    //   var mensagem = Mensagem.builder()
    //       .usuario("Usuário: " + i)
    //       .conteudo("Conteúdo da mensagem " + i)
    //       .dataCriacao(LocalDateTime.now())
    //       .build();
    //   mensagemRepository.save(mensagem);
    // }
  }
}
