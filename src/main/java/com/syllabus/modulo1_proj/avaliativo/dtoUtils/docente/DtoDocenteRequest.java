package com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DtoDocenteRequest {
    @NotBlank
    private String nome;

    @NotNull
    @Min(value = 0, message = "Código de Usuário inválido.")
    private Long usuario_id;


    public DtoDocenteRequest() {}
    public DtoDocenteRequest(String nome, Long usuario_id, String nome_papel) {
        this.nome = nome;
        this.usuario_id = usuario_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }
}
