package com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno;
import com.syllabus.modulo1_proj.avaliativo.entities.Aluno;
import jakarta.validation.constraints.NotBlank;

public class DtoAlunoResponse {

    private String nome;
    private Long id;


    public DtoAlunoResponse(){}
    public DtoAlunoResponse(String nome, Long id) {
        this.nome = nome;
        this.id = id;
    }

    public DtoAlunoResponse(Aluno aluno) {
        this.nome = aluno.getNome();
        this.id = aluno.getId();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
