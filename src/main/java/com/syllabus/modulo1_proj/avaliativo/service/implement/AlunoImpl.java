package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoNotaFinal;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Aluno;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
import com.syllabus.modulo1_proj.avaliativo.repository.AlunoRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.TurmaRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.UsuarioRepository;
import com.syllabus.modulo1_proj.avaliativo.service.AlunoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoImpl implements AlunoService {

    private static final Logger logger = LoggerFactory.getLogger(AlunoImpl.class);

    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepo;
    private final TurmaRepository turmaRepo;
    public AlunoImpl(AlunoRepository repository, UsuarioRepository usuarioRepo, TurmaRepository turmaRepo) {
        this.repository = repository;
        this.usuarioRepo = usuarioRepo;
        this.turmaRepo = turmaRepo;
    }

    @Override
    public DtoAlunoResponse criarAluno(DtoAlunoRequest aluno) {
        if (!usuarioRepo.existsById(aluno.getUsuario_id())){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Impossível criar um Aluno sem um usuário não cadastrado."
            );
        }

        if (!turmaRepo.existsById(aluno.getTurma_id())){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Impossível criar um Aluno sem o vincular a uma Turma existente/válida."
            );
        }

        Aluno novoAluno = new Aluno();

        novoAluno.setNome(aluno.getNome());
        novoAluno.setDataNascimento(aluno.getDataNascimento());
        novoAluno.setUsuario(usuarioRepo.getById(aluno.getUsuario_id()));
        repository.save(novoAluno);

        return new DtoAlunoResponse(novoAluno);
    }

    @Override
    public DtoAlunoResponse obterAlunoPorId (Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado."
            );
        }
        return new DtoAlunoResponse(repository.findById(id).get());
    }

    @Override
    public DtoAlunoResponse atualizarAluno(Long id, DtoAlunoRequest aluno) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrada para alteração."
            );
        }

        Aluno atual = repository.findById(id).get();
        atual.setNome(aluno.getNome());
        atual.setDataNascimento(aluno.getDataNascimento());
        repository.save(atual);

        return new DtoAlunoResponse(atual);
    }

    @Override
    public void deletarAluno(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado."
            );
        }
        Aluno atual = repository.findById(id).get();
        repository.delete(atual);
    }

    @Override
    public List<DtoAlunoResponse> listarTodosAlunos() {

        List<Aluno> listaAlunos = repository.findAll();

        if (listaAlunos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há Alunos cadastrados."
            );
        }
        return listaAlunos.stream().map(x -> new DtoAlunoResponse(x)).collect(Collectors.toList());
    }

    @Override
    public DtoNotaFinal pontuacaoAluno(Long alunoId){
        if (!repository.existsById(alunoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado."
            );
        }

        List<Nota> notasAluno = repository.listarNotasPorAluno(alunoId);

        if (notasAluno.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado."
            );
        }

        Double total = 0.0;
        for (Nota count : notasAluno){
            total += count.getValor();
        }
        return new DtoNotaFinal((total/notasAluno.size())*10);
    }
}
