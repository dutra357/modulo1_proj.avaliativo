package com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DtoAlunoRequest {
    @NotBlank
    private String nome;

    @NotNull
    @Min(value = 0, message = "Código de Usuário inválido.")
    private Long usuario_id;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    @Min(value = 0, message = "Código de Turma inválido.")
    private Long turma_id;

    public DtoAlunoRequest(){}
    public DtoAlunoRequest(String nome, Long usuario_id, String nome_papel, LocalDate dataNascimento, Long turma_id) {
        this.nome = nome;
        this.usuario_id = usuario_id;
        this.dataNascimento = dataNascimento;
        this.turma_id = turma_id;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getTurma_id() {
        return turma_id;
    }

    public void setTurma_id(Long turma_id) {
        this.turma_id = turma_id;
    }
}
