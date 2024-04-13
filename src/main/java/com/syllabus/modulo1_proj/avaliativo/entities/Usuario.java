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

}
