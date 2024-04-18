package com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class DtoTurma {
    @NotBlank
    private String nome;

    @NotNull
    @Min(value = 0, message = "Código do curso não pode ser inferior a 1.")
    private Long docente_id;

    @NotNull
    @Min(value = 0, message = "Código do curso não pode ser inferior a 1.")
    private Long curso_id;


    public DtoTurma(){}
    public DtoTurma(String nome, Long docente_id, Long curso_id) {
        this.nome = nome;
        this.docente_id = docente_id;
        this.curso_id = curso_id;
    }
    public DtoTurma(String nome, Long curso_id) {
        this.nome = nome;
        this.curso_id = curso_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getDocente_id() {
        return docente_id;
    }

    public void setDocente_id(Long docente_id) {
        this.docente_id = docente_id;
    }

    public Long getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(Long curso_id) {
        this.curso_id = curso_id;
    }
}
