package com.breda.redesocial.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] ALLOW_LIST_PATH = {
      "/usuario",
      "/mensagens",
      "/mensagens/**",
      "/actuator/**",
      "/api/v1/auth/**",
      "/v3/api-docs/**",
      "/v3/api-docs.yaml",
      "/swagger-ui/**",
      "/swagger-ui.html"
  };

  private static final String[] ADMIN_LIST_PATH = {
      "/usuario/admin",
  };

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
   }
  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(6);
  }

  @Bean
  @Profile("dev")
  SecurityFilterChain configurePublicEndpoints(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(csrf -> csrf.disable())
        .headers((headers) -> headers
            .defaultsDisabled()
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin())) // habilita acessar o H2
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/usuario").permitAll() // Permite o cadastro de usuário sem autenticação
            .requestMatchers(HttpMethod.POST, "/login").permitAll() // Permite o cadastro de usuário sem autenticação
            .requestMatchers(ALLOW_LIST_PATH).permitAll()
            .requestMatchers(PathRequest.toH2Console()).permitAll() // habilita acessar o H2
            .requestMatchers(ADMIN_LIST_PATH).hasRole("ADMIN")
            .anyRequest().authenticated())
        .build();
  }

  @Bean
  @Profile("dev")
  SecurityFilterChain configureDocumantionEndpoints(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("swagger-ui/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("v3/api-docs/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .anyRequest().authenticated())
        .httpBasic(withDefaults())
        .build();
  }

}
