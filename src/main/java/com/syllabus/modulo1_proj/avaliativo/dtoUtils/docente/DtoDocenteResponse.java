package com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente;
import com.syllabus.modulo1_proj.avaliativo.entities.Docente;
import jakarta.validation.constraints.NotBlank;

public class DtoDocenteResponse {
    @NotBlank
    private String nome;


    public DtoDocenteResponse() {}
    public DtoDocenteResponse(Docente docente) {
        this.nome = docente.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
