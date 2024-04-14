package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoTurma;
import com.syllabus.modulo1_proj.avaliativo.entities.Turma;
import java.util.List;

public interface TurmaService {

    Turma criarTurma(DtoTurma turma);

    Turma obterTurmaPorId(Long id);

    Turma atualizarTurma(Long id, DtoTurma turma);

    void deletarTurma(Long id);

    List<Turma> listarTodasAsTurmas();
}
