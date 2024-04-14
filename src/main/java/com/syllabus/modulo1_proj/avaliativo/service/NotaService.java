package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoNota;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;

import java.util.List;

public interface NotaService {

    List<Nota> listarNotasPorAluno(Long id);

    Nota criarNota(DtoNota nota);

    Nota obterNotaPorId(Long id);

    Nota atualizarNota(Long id, DtoNota nota);

    void deletarNota(Long id);
}
