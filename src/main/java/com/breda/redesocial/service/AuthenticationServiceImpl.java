package com.breda.redesocial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.breda.redesocial.dto.AuthenticationRequest;
import com.breda.redesocial.dto.AuthenticationResponse;
import com.breda.redesocial.repository.UsuarioRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UsuarioRepository repositorio;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse auhtenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getApelido(),
                            request.getSenha()));

            var usuario = repositorio.findByApelido(request.getApelido())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            // Verificar se a senha fornecida é válida
            if (!passwordEncoder.matches(request.getSenha(), usuario.getPassword())) {
                throw new BadCredentialsException("invalid password");
            }

            passwordEncoder.encode(request.getSenha());

            var jwtToken = jwtService.generateToken(usuario);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("invalid credential");
        }
    }

    @Override
    public String getApelidoUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }
        return null;
    }
}
