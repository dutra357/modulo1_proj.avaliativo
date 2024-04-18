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
            logger.error("Tentativa de listagem de matérias de curso inexistente. ID info: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso inexistente para pesquisa."
            );
        }

        List<Materia> materias = repository.listarMateriaPorCurso(id);

        if (materias.isEmpty()) {
            logger.error("Não há matérias cadastradas para este curso");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há matérias cadastradas para este curso."
            );
        }
        logger.info("Retornando matérias listadas, Curso ID {}", id);
        return materias;
    }

    @Override
    public Materia criarMateria(DtoMateria materia) {
        var curso = repository.buscaCurso(materia.getCurso_id());
        if (curso == null) {
            logger.error("Tentativa de cadastro de Matéria sem atribuição de Curso válido/existente");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "É necessário um Curso para se cadastrar uma matéria."
            );
        }

        Materia novaMateria = new Materia();
        novaMateria.setNome(materia.getNome());
        novaMateria.setCurso(curso);
        logger.info("Nova matéria cadastrada com sucesso.");
        return repository.save(novaMateria);
    }

    @Override
    public Materia obterMateriaPorId (Long id){
        if (!repository.existsById(id)) {
            logger.error("Materia não encontrada, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada."
            );
        }
        logger.info("Retornando Matéria solicitada, ID {}", id);
        return repository.findById(id).get();
        }

    @Override
    public Materia atualizarMateria(Long id, DtoMateria materia) {
        if (!repository.existsById(id)) {
            logger.error("Materia não encontrada para PUT, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada."
            );
        }

        if (!cursoRepo.existsById(materia.getCurso_id())) {
            logger.error("A alteração de uma matéria exige um curso válido.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma matéria exige um curso válido."
            );
        }

        Materia atual = repository.findById(id).get();
        atual.setNome(materia.getNome());
        atual.setCurso(cursoRepo.getById(materia.getCurso_id()));
        logger.info("Matéria atualizada com sucesso.");
        return repository.save(atual);
    }

    @Override
    public void deletarMateria(Long id) {
        if (!repository.existsById(id)) {
            logger.error("Materia não encontrada para DELETE, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada."
            );
        }
        Materia atual = repository.findById(id).get();
        repository.delete(atual);
        logger.info("Matéria excluída com sucesso, ID {}", id);
    }

    public  List<Materia> listarTodasMaterias() {
        logger.info("Listando todas as Matérias cadastradas.");
        return repository.findAll();
    }
}
