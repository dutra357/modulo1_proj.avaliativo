package com.syllabus.modulo1_proj.avaliativo.security;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        //falta colocar materias, sem previsao no projeto
                        //ajustar os que vem apos o endpoint /* e /**

                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cadastro").hasRole("ADMIN")

                        //turma ok,
                        .requestMatchers(HttpMethod.POST, "/turmas", "/cursos", "/alunos").hasRole("PEDAGOGICO")
                        .requestMatchers(HttpMethod.GET, "/turmas", "/turmas/*", "/cursos", "/cursos/*", "/alunos", "/alunos/*").hasRole("PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/turmas/*", "/cursos/*", "/alunos/*").hasRole("PEDAGOGICO")

                        //ok
                        .requestMatchers(HttpMethod.POST, "/notas").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/notas", "/notas/*").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/notas/*").hasRole("PROFESSOR")

                        //Docentes ok
                        .requestMatchers(HttpMethod.POST, "/docentes").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/docentes", "/docentes/*").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/docentes/*").hasRole("RECRUITER")

                        //Aluno só acessa pontuação e notas, não acessa listagem de todos os alunos
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
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
