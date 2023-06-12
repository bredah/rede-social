package com.breda.redesocial.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@AllArgsConstructor
@Data
public class MensagemResponse {
     private String mensagem;
    private UUID id;
}
