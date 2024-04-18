package com.syllabus.modulo1_proj.avaliativo.entities.enuns;


public enum UsuarioPapel {
    ADMIN("admin"),
    PEDAGOGICO("pedagogico"),
    RECRUITER("recruiter"),
    PROFESSOR("professor"),
    ALUNO("aluno");

    private String role;

    UsuarioPapel(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
