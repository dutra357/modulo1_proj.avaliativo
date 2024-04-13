package com.syllabus.modulo1_proj.avaliativo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

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
}
