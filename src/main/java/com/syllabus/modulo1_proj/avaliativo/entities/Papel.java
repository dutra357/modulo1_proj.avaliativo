package com.syllabus.modulo1_proj.avaliativo.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name ="papeis")
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome_papel", length = 150, nullable = false)
    private String nome;



    public Papel(){}
    public Papel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Papel papel = (Papel) o;
        return Objects.equals(nome, papel.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }


}
