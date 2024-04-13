package com.syllabus.modulo1_proj.avaliativo.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Docente docente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;




    public Turma(){}
    public Turma(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
