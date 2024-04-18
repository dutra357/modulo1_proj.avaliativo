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
        logger.info("Novo Curso criado com sucesso.");
        return repository.save(novoCurso);
    }

    @Override
    public Curso obterCursoPorId(Long id) {
        if (!repository.existsById(id)) {
            logger.error("Curso não encontrado, ID info: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso não encontrado."
            );
        }
        logger.info("Retornando Curso solicidato, ID {}", id);
        return repository.findById(id).get();
    }

    @Override
    public List<Curso> listarCursos(){
        if (repository.findAll().isEmpty()) {
            logger.error("Lista vazia. Não há cursos cadastrados.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há cursos cadastrados."
            );
        }
        logger.info("Retornando listagem completa de cursos cadastrados.");
        return repository.findAll();
    }

    @Override
    public Curso atualizarCurso(Long id, DtoCurso curso) {
        if (!repository.existsById(id)) {
            logger.error("Curso não encontrado para PUT, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso não encontrado para edição."
            );
        }
        Curso atual = repository.findById(id).get();
        atual.setNome(curso.getNome());
        logger.info("Curso atualizado com sucesso, ID {}", id);
        return repository.save(atual);
    }

    @Override
    public void deletarCurso(Long id) {
        if (!repository.existsById(id)) {
            logger.error("Curso não encontrado para o método DEL, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Curso não encontrado."
            );
        }
        Curso atual = repository.findById(id).get();
        repository.delete(atual);
        logger.info("Curso excluído com sucesso, ID {}", id);
    }
}
