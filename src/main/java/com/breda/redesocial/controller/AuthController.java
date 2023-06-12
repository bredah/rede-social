package com.breda.redesocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.breda.redesocial.dto.AuthenticationRequest;
import com.breda.redesocial.dto.AuthenticationResponse;
import com.breda.redesocial.service.AuthService;
import com.breda.redesocial.util.JwtUtil;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Autenticar o usuário
            authService.autenticarUsuario(authenticationRequest.getApelido(), authenticationRequest.getSenha());

            // Gerar o token de acesso
            final UserDetails userDetails = authService.buscarUsuarioPorApelido(authenticationRequest.getApelido());
            final String token = jwtUtil.generateToken(userDetails);

            // Retornar a resposta de autenticação com o token
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (AuthenticationException e) {
            // A autenticação falhou
            // Lidar com o erro de autenticação
            return ResponseEntity.status(401).body("Falha na autenticação");
        }
    }
}
