package com.breda.redesocial.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
  title = "REST APºI",
  version = "1.0",
  description = "REST API description...",
  contact = @Contact(name = "Name Surname")))
public class DocumentationConfig {

}
