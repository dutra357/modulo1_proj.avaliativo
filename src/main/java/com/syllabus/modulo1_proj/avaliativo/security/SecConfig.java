package com.syllabus.modulo1_proj.avaliativo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/turmas","/cursos","/professores","/alunos").hasRole("PEDAGOGICO")
                        .requestMatchers(HttpMethod.POST,"/turmas","/cursos","/professores","/alunos").hasRole("PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT,"/turmas","/cursos","/professores","/alunos").hasRole("PEDAGOGICO")

                        .requestMatchers(HttpMethod.GET,"professores").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.POST,"professores").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.PUT,"professores").hasRole("RECRUITER")

                        .requestMatchers(HttpMethod.GET,"/notas").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.POST,"/notas").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.PUT,"/notas").hasRole("PROFESSOR")

                        .requestMatchers(HttpMethod.GET,"/alunos/**").hasRole("ALUNO")

                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
