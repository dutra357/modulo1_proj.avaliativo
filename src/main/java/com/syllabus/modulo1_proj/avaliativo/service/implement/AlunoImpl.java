package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoNotaFinal;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Aluno;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import com.syllabus.modulo1_proj.avaliativo.entities.enuns.UsuarioPapel;
import com.syllabus.modulo1_proj.avaliativo.repository.AlunoRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.TurmaRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.UsuarioRepository;
import com.syllabus.modulo1_proj.avaliativo.service.AlunoService;
import com.syllabus.modulo1_proj.avaliativo.service.MateriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.tool.schema.spi.ExceptionHandler;
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

    private final HttpServletRequest request;
    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepo;
    private final UsuarioImpl usuarioService;
    private final TurmaRepository turmaRepo;
    private final MateriaService materiaService;
    public AlunoImpl(AlunoRepository repository, UsuarioRepository usuarioRepo,
                     TurmaRepository turmaRepo, MateriaService materiaService,
                     HttpServletRequest request, UsuarioImpl usuarioService) {
        this.repository = repository;
        this.usuarioRepo = usuarioRepo;
        this.turmaRepo = turmaRepo;
        this.materiaService = materiaService;
        this.request = request;
        this.usuarioService = usuarioService;
    }

    @Override
    public DtoAlunoResponse criarAluno(DtoAlunoRequest aluno) {
        if (!usuarioRepo.existsById(aluno.getUsuario_id())){
            logger.error("Necessário um usuário cadastrado para se criar novo Aluno");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Impossível criar um Aluno sem um usuário não cadastrado."
            );
        }

        if (!turmaRepo.existsById(aluno.getTurma_id())){
            logger.error("Impossível criar um Aluno sem o vincular a uma Turma existente");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Impossível criar um Aluno sem o vincular a uma Turma existente/válida."
            );
        }

        Aluno novoAluno = new Aluno();

        novoAluno.setNome(aluno.getNome());
        novoAluno.setDataNascimento(aluno.getDataNascimento());
        novoAluno.setUsuario(usuarioRepo.getById(aluno.getUsuario_id()));
        novoAluno.setTurma(turmaRepo.getById(aluno.getTurma_id()));
        repository.save(novoAluno);
        logger.info("Novo Aluno cadastrado com sucesso.");

        return new DtoAlunoResponse(novoAluno);
    }

    @Override
    public DtoAlunoResponse obterAlunoPorId (Long id){
        if (!repository.existsById(id)) {
            logger.error("Aluno não encontrado, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado."
            );
        }
        logger.info("Retornando Aluno solicitado, ID {}", id);
        return new DtoAlunoResponse(repository.findById(id).get());
    }

    @Override
    public DtoAlunoResponse atualizarAluno(Long id, DtoAlunoRequest aluno) {
        if (!repository.existsById(id)) {
            logger.error("Aluno não encontrado para alteração/PUT, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado para alteração."
            );
        }

        Aluno atual = repository.findById(id).get();
        atual.setNome(aluno.getNome());
        atual.setDataNascimento(aluno.getDataNascimento());
        repository.save(atual);
        logger.info("Aluno alterado com sucesso, ID {}", id);
        return new DtoAlunoResponse(atual);
    }

    @Override
    public void deletarAluno(Long id) {
        if (!repository.existsById(id)) {
            logger.error("Aluno não encontrado para método DELETE, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado."
            );
        }
        Aluno atual = repository.findById(id).get();
        repository.delete(atual);
        logger.info("Aluno excluído com sucesso, ID {}", id);
    }

    @Override
    public List<DtoAlunoResponse> listarTodosAlunos() {

        List<Aluno> listaAlunos = repository.findAll();

        if (listaAlunos.isEmpty()) {
            logger.error("Lista zerada. Não há Alunos cadastrados.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há Alunos cadastrados."
            );
        }
        logger.info("Retornando listagem de todos os Alunos cadastrados.");
        return listaAlunos.stream().map(DtoAlunoResponse::new).collect(Collectors.toList());
    }


    @Override
    public DtoNotaFinal pontuacaoAluno(Long alunoId){
        logger.debug("Requerendo nome de usuário (login) do usuário logado.");
        String usuarioSessao = request.getUserPrincipal().getName();
        Usuario usuario = usuarioService.buscarPorLogin(usuarioSessao);
        Aluno logado = repository.buscarLogado(usuario.getId());

        if ((usuario.getRole() == UsuarioPapel.ALUNO) &&
                (logado.getId() == alunoId)) {
            logger.debug("Usuário corresponde à role ALUNO.");
            Long id = logado.getId();
            var notaFinal = calcularPontuacaoFinal(id);
            return new DtoNotaFinal(notaFinal);
        }

        else if ((usuario.getRole() == UsuarioPapel.ALUNO) &&
                (usuario.getId() != alunoId)) {
            logger.error("Aluno logado solicita pontuação de outro Aluno/Usuário.");
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Disponível apenas a pontuação do usuário/aluno logado.");
        }

        //Para requisição por parte de ADMN, PROFESSOR etc.
        else if (!repository.existsById(alunoId)) {
            logger.error("Aluno não encontrado, ID {}", alunoId);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }
        logger.info("Retornando pontuação final do aluno.");
        return new DtoNotaFinal(calcularPontuacaoFinal(alunoId));
    }

    //Métrica de cálculo
    public Double calcularPontuacaoFinal(Long aluno_id) {

        List<Nota> notasAluno = repository.listarNotasPorAluno(aluno_id);

        if (notasAluno.isEmpty()) {
            logger.error("Não há notas cadastradas para realização do cálculo de pontuação.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há notas cadastradas."
            );
        }

        //Projeto solicita divisão pelo número de matérias, não de notas
        List<Materia> numeroMaterias = materiaService.listarTodasMaterias();

        if (numeroMaterias.isEmpty()) {
            logger.error("Não há notas matérias cadastradas, possível divisão por zero.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Divisão por zero, não há matérias cadastradas."
            );
        }

        try {
            logger.debug("Calculando pontuação. Número de matérias: {}", numeroMaterias);
            Double pontuacaoTotal = 0.0;
            for (Nota count : notasAluno){
                pontuacaoTotal += count.getValor();
            }
            Double resultado = (pontuacaoTotal/numeroMaterias.size())*10;

            logger.debug("Retornando cálculo de pontuação. Método calcularPontuacaoFinal, res: {}", resultado);
            return resultado;
        } catch (ArithmeticException e) {
            logger.error("Erro de aritimética.");
            throw new ArithmeticException("Erro de aritimética.");
        } catch (Exception e) {
            logger.error("Erro genérico ao calcular pontuação.");
            throw new ArithmeticException("Erro ao calcular pontuação.");
        } finally {
            logger.debug("Método calcularPontuacao encerrado.");
        }
    }
}
