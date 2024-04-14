package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoTurma;
import com.syllabus.modulo1_proj.avaliativo.entities.Turma;
import com.syllabus.modulo1_proj.avaliativo.repository.CursoRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.DocenteRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.TurmaRepository;
import com.syllabus.modulo1_proj.avaliativo.service.TurmaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public Turma criarTurma(DtoTurma turma) {
        if (!cursoRepo.existsById(turma.getCurso_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "É necessário um Curso válido para se cadastrar uma Turma."
            );
        }

        Turma novaTurma = new Turma();
        novaTurma.setNome(turma.getNome());
        novaTurma.setCurso(cursoRepo.getById(turma.getCurso_id()));
        if (turma.getDocente_id() != null) {
            novaTurma.setDocente(docenteRepo.getById(turma.getDocente_id()));
        }
        return repository.save(novaTurma);
    }

    @Override
    public Turma obterTurmaPorId (Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Turma não encontrada."
            );
        }
        return repository.findById(id).get();
    }

    @Override
    public Turma atualizarTurma(Long id, DtoTurma turma) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Turma não encontrada para modificação."
            );
        }

        if (!cursoRepo.existsById(turma.getCurso_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Turma exige um curso válido."
            );
        }

        Turma atual = repository.findById(id).get();
        atual.setNome(turma.getNome());
        atual.setCurso(cursoRepo.getById(turma.getCurso_id()));
        if (turma.getDocente_id() != null) {
            atual.setDocente(docenteRepo.getById(turma.getDocente_id()));
        }
        return repository.save(atual);
    }

    @Override
    public void deletarTurma(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Turma não encontrada.");
        }
        Turma atual = repository.findById(id).get();
        repository.delete(atual);
    }

    @Override
    public List<Turma> listarTodasAsTurmas() {
        List<Turma> turmas = repository.findAll();
        if (turmas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há Turmas cadastradas."
            );
        }
        return turmas;
    }

}
