package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteResponse;
import java.util.List;

public interface DocenteService {

    DtoDocenteResponse criarDocente(DtoDocenteRequest docente);

    DtoDocenteResponse obterDocentePorId(Long id);

    DtoDocenteResponse atualizarDocente(Long id, DtoDocenteRequest docente);

    void deletarDocente(Long id);

    List<DtoDocenteResponse> listarTodosDocentes();
}
