package com.breda.redesocial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.breda.redesocial.interceptor.AuthenticationInterceptor;

@Configuration
public class InterceptorConfig{
 
  public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/mensagens/**"); // Defina o padrão de URL para o controlador de mensagens
    }
}
