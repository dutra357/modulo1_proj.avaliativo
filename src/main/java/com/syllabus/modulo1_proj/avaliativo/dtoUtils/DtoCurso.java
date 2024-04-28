package com.syllabus.modulo1_proj.avaliativo.dtoUtils;
import jakarta.validation.constraints.NotBlank;

public class DtoCurso {
    @NotBlank
    private String nome;

    public DtoCurso(){}
    public DtoCurso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
