package com.syllabus.modulo1_proj.avaliativo.dtoUtils.login;

public class DtoTokenResponse {
    private String token;

    public DtoTokenResponse(){}
    public DtoTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
