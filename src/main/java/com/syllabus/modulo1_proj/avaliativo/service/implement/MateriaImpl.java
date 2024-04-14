package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoMateria;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import com.syllabus.modulo1_proj.avaliativo.repository.CursoRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.MateriaRepository;
import com.syllabus.modulo1_proj.avaliativo.service.MateriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MateriaImpl implements MateriaService {

    private static final Logger logger = LoggerFactory.getLogger(MateriaImpl.class);

    private final MateriaRepository repository;
    private final CursoRepository cursoRepo;
    public MateriaImpl(MateriaRepository repository, CursoRepository cursoRepo) {
        this.repository = repository;
        this.cursoRepo = cursoRepo;
    }

    @Override
    public List<Materia> listarMateriaPorCurso(Long id) {
        if (!cursoRepo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso inexistente para pesquisa."
            );
        }

        List<Materia> materias = repository.listarMateriaPorCurso(id);

        if (materias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há matérias cadastradas para este curso."
            );
        }
        return materias;
    }

    @Override
    public Materia criarMateria(DtoMateria materia) {
        var curso = repository.buscaCurso(materia.getCurso_id());
        if (curso == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso inexistente para se cadastrar uma matéria."
            );
        }

        Materia novaMateria = new Materia();
        novaMateria.setNome(materia.getNome());
        novaMateria.setCurso(curso);
        return repository.save(novaMateria);
    }

    @Override
    public Materia obterMateriaPorId (Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada."
            );
        }
        return repository.findById(id).get();
        }

    @Override
    public Materia atualizarMateria(Long id, DtoMateria materia) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada."
            );
        }

        if (!cursoRepo.existsById(materia.getCurso_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma matéria exige um curso válido."
            );
        }

        Materia atual = repository.findById(id).get();
        atual.setNome(materia.getNome());
        atual.setCurso(cursoRepo.getById(materia.getCurso_id()));
        return repository.save(atual);
    }

    @Override
    public void deletarMateria(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada."
            );
        }
        Materia atual = repository.findById(id).get();
        repository.delete(atual);
    }
}
