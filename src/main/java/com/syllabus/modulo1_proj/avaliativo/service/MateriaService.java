package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoMateria;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;

import java.util.List;

public interface MateriaService {

    List<Materia> listarMateriaPorCurso(Long id);

    Materia criarMateria(DtoMateria materia);

    Materia obterMateriaPorId(Long id);

    Materia atualizarMateria(Long id, DtoMateria materia);

    void deletarMateria(Long id);

    List<Materia> listarTodasMaterias();

}
