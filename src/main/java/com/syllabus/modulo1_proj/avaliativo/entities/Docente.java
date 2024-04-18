package com.syllabus.modulo1_proj.avaliativo.entities;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "docentes")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", length = 250, nullable = false)
    private String nome;

    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Docente(){}
    public Docente(Long id, String login, LocalDate dataEntrada, Papel papel) {
        this.id = id;
        this.nome = login;
        this.dataEntrada = dataEntrada;
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

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
