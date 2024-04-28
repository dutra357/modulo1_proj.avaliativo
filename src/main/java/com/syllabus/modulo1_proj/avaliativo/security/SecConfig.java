package com.syllabus.modulo1_proj.avaliativo.security;
import jakarta.servlet.DispatcherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecConfig {
     @Autowired
    FiltroSegurança filtroSegurança;

    private static final Logger logger = LoggerFactory.getLogger(SecConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Setando configurações de securança - SecurityConfig Spring BOOT.");
        return  httpSecurity
                .authorizeHttpRequests(authorize -> authorize

                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cadastro").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/turmas", "/cursos", "/alunos", "/materias").hasRole("PEDAGOGICO")
                        .requestMatchers(HttpMethod.GET, "/turmas", "/turmas/*", "/cursos", "/cursos/*", "/alunos","/alunos/*", "/materias", "/materias/*").hasRole("PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/turmas/*", "/cursos/*", "/alunos/*", "/materias/*").hasRole("PEDAGOGICO")

                        .requestMatchers(HttpMethod.POST, "/notas").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/notas", "/notas/*").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/notas/*").hasRole("PROFESSOR")

                        .requestMatchers(HttpMethod.POST, "/docentes").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/docentes", "/docentes/*").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/docentes/*").hasRole("RECRUITER")

                        .requestMatchers(HttpMethod.GET, "/alunos/**").hasRole("ALUNO")

                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filtroSegurança, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.debug("Execução AuthenticationManager.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        logger.debug("Executando Encoder.");
        return new BCryptPasswordEncoder();
    }

}
