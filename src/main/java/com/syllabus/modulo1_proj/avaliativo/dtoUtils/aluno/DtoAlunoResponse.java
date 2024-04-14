package com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno;
import com.syllabus.modulo1_proj.avaliativo.entities.Aluno;
import jakarta.validation.constraints.NotBlank;

public class DtoAlunoResponse {
    @NotBlank
    private String nome;

    public DtoAlunoResponse(){}
    public DtoAlunoResponse(String nome) {
        this.nome = nome;
    }

    public DtoAlunoResponse(Aluno aluno) {
        this.nome = aluno.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
