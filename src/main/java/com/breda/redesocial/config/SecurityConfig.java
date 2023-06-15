package com.breda.redesocial.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] ALLOW_LIST_PATH = {
    };

    private static final String[] ADMIN_LIST_PATH = {
            "/actuator/**",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

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
                        // Permit all
                        .requestMatchers(HttpMethod.GET, "/mensagens").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                        .requestMatchers(ALLOW_LIST_PATH).permitAll()
                        // Auth only
                        .requestMatchers(HttpMethod.POST, "/mensagens").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/mensagens").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/mensagens").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/usuario").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/usuario").authenticated()
                        // Admin role only
                        .requestMatchers(ADMIN_LIST_PATH).hasRole("ADMIN")
                        // DB console
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // habilita acessar o H2
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Profile("dev")
    SecurityFilterChain configureDocumantionEndpoints(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .build();
    }

}
