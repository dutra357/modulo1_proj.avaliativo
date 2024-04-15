package com.syllabus.modulo1_proj.avaliativo.dtoUtils;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
public class DtoNotaFinal {

    private Double pontuacao;

    public DtoNotaFinal(){}
    public DtoNotaFinal(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public DtoNotaFinal (Nota nota) {
        this.pontuacao = nota.getValor();
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }
}
