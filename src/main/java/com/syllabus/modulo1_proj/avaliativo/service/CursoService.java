package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoCurso;
import com.syllabus.modulo1_proj.avaliativo.entities.Curso;
import java.util.List;

public interface CursoService {

    Curso criarCurso(DtoCurso curso);

    Curso obterCursoPorId(Long id);

    List<Curso> listarCursos();

    Curso atualizarCurso(Long id, DtoCurso curso);

    void deletarCurso(Long id);
}
