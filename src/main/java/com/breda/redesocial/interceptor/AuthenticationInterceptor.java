package com.breda.redesocial.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // Verificar o token de autenticação aqui
    // Se o token não for válido, você pode enviar uma resposta de erro ou
    // redirecionar para a página de login

    // Retorne true para permitir que o fluxo da requisição continue para o
    // controlador
    return true;
  }
}
