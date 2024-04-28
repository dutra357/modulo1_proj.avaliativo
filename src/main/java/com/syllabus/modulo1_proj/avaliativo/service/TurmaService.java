package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma.DtoTurma;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma.DtoTurmaResponse;
import java.util.List;

public interface TurmaService {

    DtoTurmaResponse criarTurma(DtoTurma turma);

    DtoTurmaResponse obterTurmaPorId(Long id);

    DtoTurmaResponse atualizarTurma(Long id, DtoTurma turma);

    void deletarTurma(Long id);

    List<DtoTurmaResponse> listarTodasAsTurmas();
}
