package com.syllabus.modulo1_proj.avaliativo.dtoUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class DtoMateria {
    @NotBlank
    private String nome;

    @NotNull
    @Min(value = 1, message = "Código do curso não pode ser inferior a 1.")
    private Long curso_id;

    public DtoMateria(){}
    public DtoMateria(String nome, Long curso_id) {
        this.nome = nome;
        this.curso_id = curso_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(Long curso_id) {
        this.curso_id = curso_id;
    }
}
