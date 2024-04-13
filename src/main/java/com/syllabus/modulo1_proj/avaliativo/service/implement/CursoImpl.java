package com.syllabus.modulo1_proj.avaliativo.service.implement;

import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoCurso;
import com.syllabus.modulo1_proj.avaliativo.entities.Curso;
import com.syllabus.modulo1_proj.avaliativo.repository.CursoRepository;
import com.syllabus.modulo1_proj.avaliativo.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CursoImpl implements CursoService {

    private static final Logger logger = LoggerFactory.getLogger(CursoImpl.class);

    private final CursoRepository repository;
    public CursoImpl(CursoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Curso criarCurso(DtoCurso curso) {
        Curso novoCurso = new Curso();
        novoCurso.setNome(curso.getNome());
        return repository.save(novoCurso);
    }

    @Override
    public Curso obterCursoPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso não encontrado."
            );
        }
        return repository.findById(id).get();
    }

    @Override
    public List<Curso> listarCursos(){
        if (repository.findAll().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há cursos cadastrados!"
            );
        }
        return repository.findAll();
    }

    @Override
    public Curso atualizarCurso(Long id, DtoCurso curso) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso não encontrado."
            );
        }
        Curso atual = repository.findById(id).get();
        atual.setNome(curso.getNome());
        return repository.save(atual);
    }

    @Override
    public void deletarCurso(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso não encontrado."
            );
        }
        Curso atual = repository.findById(id).get();
        repository.delete(atual);
    }
}
