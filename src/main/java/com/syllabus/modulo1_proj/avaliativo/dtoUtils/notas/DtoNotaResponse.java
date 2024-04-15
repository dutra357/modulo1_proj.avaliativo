package com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;

import java.time.LocalDate;


public class DtoNotaResponse {
    private Double valor;

    private LocalDate data;

    private Long id;

    public DtoNotaResponse(){}
    public DtoNotaResponse(Double valor, LocalDate data, Long id) {
        this.valor = valor;
        this.data = data;
        this.id = id;
    }

    public DtoNotaResponse(Nota nota) {
        this.valor = nota.getValor();
        this.data = nota.getDataNota();
        this.id = nota.getId();
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
