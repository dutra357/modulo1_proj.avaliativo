package com.syllabus.modulo1_proj.avaliativo.entities;
import jakarta.persistence.*;

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
    public Papel(String nome) {
        this.nome = nome;
    }
    public Papel(Long id, String nome) {
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
