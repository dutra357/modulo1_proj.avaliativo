package com.syllabus.modulo1_proj.avaliativo.entities;
import com.syllabus.modulo1_proj.avaliativo.security.UsuarioPapel;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", length = 150, nullable = false)
    private String login;

    @Column(name = "senha", length = 250, nullable = false)
    private String senha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "papel_id", nullable = false)
    private Papel papel;

    private UsuarioPapel role;

    public Usuario(){}
    public Usuario(Long id, String senha, String login, Papel papel) {
        this.id = id;
        this.senha = senha;
        this.login = login;
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsuarioPapel.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_PEDAGOGICO"), new SimpleGrantedAuthority("ROLE_RECRUITER"), new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_ALUNO"));
        }
        if(this.role == UsuarioPapel.PEDAGOGICO) {
            return List.of(new SimpleGrantedAuthority("ROLE_PEDAGOGICO"), new SimpleGrantedAuthority("ROLE_RECRUITER"), new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_ALUNO"));
        }
        if(this.role == UsuarioPapel.RECRUITER) {
            return List.of(new SimpleGrantedAuthority("ROLE_RECRUITER"), new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_ALUNO"));
        }
        if(this.role == UsuarioPapel.PROFESSOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_ALUNO"));
        }
        else {
            return List.of(new SimpleGrantedAuthority("ROLE_ALUNO"));
        }
    }
    @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
