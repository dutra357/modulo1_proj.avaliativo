package com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario;
import jakarta.validation.constraints.NotBlank;


public class DtoUsuarioRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    @NotBlank
    private String papel;

    public DtoUsuarioRequest(){}
    public DtoUsuarioRequest(String login, String senha, String papel) {
        this.login = login;
        this.senha = senha;
        this.papel = papel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}
