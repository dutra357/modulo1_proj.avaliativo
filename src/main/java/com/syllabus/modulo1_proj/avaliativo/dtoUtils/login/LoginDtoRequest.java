package com.syllabus.modulo1_proj.avaliativo.dtoUtils.login;

public class LoginDtoRequest {
    private String login;
    private String senha;

    public LoginDtoRequest(){}
    public LoginDtoRequest(String login, String senha) {
        this.login = login;
        this.senha = senha;
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
}
