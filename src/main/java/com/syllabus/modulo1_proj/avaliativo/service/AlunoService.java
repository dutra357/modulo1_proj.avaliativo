package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoNotaFinal;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoResponse;
import java.util.List;

public interface AlunoService {

    DtoAlunoResponse criarAluno(DtoAlunoRequest aluno);

    DtoAlunoResponse obterAlunoPorId(Long id);

    DtoAlunoResponse atualizarAluno(Long id, DtoAlunoRequest aluno);

    void deletarAluno(Long id);

    List<DtoAlunoResponse> listarTodosAlunos();

    DtoNotaFinal pontuacaoAluno(Long id);
}
