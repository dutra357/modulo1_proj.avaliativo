package com.syllabus.modulo1_proj.avaliativo.exception.dto;
import lombok.Builder;

@Builder
public class Erro {

    private String codigo;
    private String mensagem;

    public Erro(){}
    public Erro(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}