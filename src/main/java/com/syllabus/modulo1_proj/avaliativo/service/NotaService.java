package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNota;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNotaResponse;
import java.util.List;

public interface NotaService {

    List<DtoNotaResponse> listarNotasPorAluno(Long id);

    DtoNotaResponse criarNota(DtoNota nota);

    DtoNotaResponse obterNotaPorId(Long id);

    DtoNotaResponse atualizarNota(Long id, DtoNota nota);

    void deletarNota(Long id);

}
