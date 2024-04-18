package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma.DtoTurma;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma.DtoTurmaResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Docente;
import com.syllabus.modulo1_proj.avaliativo.entities.Turma;
import com.syllabus.modulo1_proj.avaliativo.repository.CursoRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.DocenteRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.TurmaRepository;
import com.syllabus.modulo1_proj.avaliativo.service.TurmaService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaImpl implements TurmaService {

    private static final Logger logger = LoggerFactory.getLogger(TurmaImpl.class);

    private final TurmaRepository repository;
    private final CursoRepository cursoRepo;
    private final DocenteRepository docenteRepo;
    public TurmaImpl(TurmaRepository repository, CursoRepository cursoRepo, DocenteRepository docenteRepo) {
        this.repository = repository;
        this.cursoRepo = cursoRepo;
        this.docenteRepo = docenteRepo;
    }

    @Override
    @Transactional
    public DtoTurmaResponse criarTurma(DtoTurma turma) {
        if (!cursoRepo.existsById(turma.getCurso_id())) {
            logger.error("É necessário um Curso válido para se cadastrar uma Turma.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "É necessário um Curso válido para se cadastrar uma Turma."
            );
        }

        if (!docenteRepo.existsById(turma.getDocente_id())) {
            logger.error("É necessário um Docente cadastrado para a Turma.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "É necessário um Docente cadastrado para a Turma."
            );
        }

        Turma novaTurma = new Turma();
        Docente docente = docenteRepo.getById(turma.getDocente_id());
        novaTurma.setDocente(docente);
        novaTurma.setNome(turma.getNome());
        novaTurma.setCurso(cursoRepo.getById(turma.getCurso_id()));

        repository.save(novaTurma);
        logger.info("Nova Turma criada.");

        return new DtoTurmaResponse(novaTurma);
    }

    @Override
    public DtoTurmaResponse obterTurmaPorId (Long id){
        if (!repository.existsById(id)) {
            logger.error("Turma não encontrada.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Turma não encontrada."
            );
        }
        logger.info("Retornando turma de ID: {}", id);
        return new DtoTurmaResponse(repository.findById(id).get());
    }

    @Override
    @Transactional
    public DtoTurmaResponse atualizarTurma(Long id, DtoTurma turma) {
        if (!repository.existsById(id)) {
            logger.error("Turma não encontrada para modificação (UPDATE), ID: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Turma não encontrada para modificação."
            );
        }

        if (!cursoRepo.existsById(turma.getCurso_id())) {
            logger.error("A alteração de uma Turma exige um curso válido.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Turma exige um curso válido."
            );
        }

        Turma atual = repository.findById(id).get();
        atual.setNome(turma.getNome());
        atual.setCurso(cursoRepo.getById(turma.getCurso_id()));

        if (turma.getDocente_id() != null) {
            logger.info("Turma já possui Professor atribuído.");
            atual.setDocente(docenteRepo.getById(turma.getDocente_id()));
        }

        repository.save(atual);
        logger.info("Turma atualizada, ID: {}", id);
        return new DtoTurmaResponse(atual);
    }

    @Override
    public void deletarTurma(Long id) {
        if (!repository.existsById(id)) {
            logger.error("Turma não encontrada, ID: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Turma não encontrada.");
        }
        Turma atual = repository.findById(id).get();
        repository.delete(atual);
        logger.info("Turma deletada, ID: {}", id);
    }

    @Override
    public List<DtoTurmaResponse> listarTodasAsTurmas() {
        List<Turma> turmas = repository.findAll();

        if (turmas.isEmpty()) {
            logger.error("Não há turmas cadastradas.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há Turmas cadastradas."
            );
        }
        logger.info("Listando todas as turmas encontradas.");
        return turmas.stream().map(x -> new DtoTurmaResponse(x)).collect(Collectors.toList());
    }

}
