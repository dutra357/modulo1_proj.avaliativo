package com.syllabus.modulo1_proj.avaliativo.dtoUtils;

public class DtoNotaFinal {
    private Double pontuacao;

    public DtoNotaFinal(){}
    public DtoNotaFinal(Double notaFinal) {
        this.pontuacao = notaFinal;
    }

    public Double getNotaFinal() {
        return pontuacao;
    }

    public void setNotaFinal(Double notaFinal) {
        this.pontuacao = notaFinal;
    }
}
