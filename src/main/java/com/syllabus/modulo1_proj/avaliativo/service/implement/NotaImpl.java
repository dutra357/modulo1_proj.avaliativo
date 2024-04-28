package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNota;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNotaResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Aluno;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import com.syllabus.modulo1_proj.avaliativo.entities.enuns.UsuarioPapel;
import com.syllabus.modulo1_proj.avaliativo.repository.AlunoRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.DocenteRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.MateriaRepository;
import com.syllabus.modulo1_proj.avaliativo.repository.NotasRepository;
import com.syllabus.modulo1_proj.avaliativo.service.NotaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaImpl implements NotaService {

    private static final Logger logger = LoggerFactory.getLogger(NotaImpl.class);

    private final NotasRepository repository;
    private final AlunoRepository alunoRepo;
    private final DocenteRepository docenteRepo;
    private final MateriaRepository materiaRepo;
    private final UsuarioImpl usuarioService;
    private final HttpServletRequest request;
    public NotaImpl(NotasRepository repository, AlunoRepository alunoRepo,
                    DocenteRepository docenteRepo, MateriaRepository materiaRepo,
                    UsuarioImpl usuarioService1, HttpServletRequest request) {
        this.repository = repository;
        this.alunoRepo = alunoRepo;
        this.docenteRepo = docenteRepo;
        this.materiaRepo = materiaRepo;
        this.usuarioService = usuarioService1;
        this.request = request;
    }


    @Override
    public List<DtoNotaResponse> listarNotasPorAluno(Long alunoId) {
        if (!repository.existsById(alunoId)) {
            logger.error("Aluno não encontrado para listagem de notas, ID informado: {}", alunoId);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado para listagem de notas.");
        }

        String usuarioSessao = request.getUserPrincipal().getName();
        Usuario usuario = usuarioService.buscarPorLogin(usuarioSessao);
        Aluno logado = alunoRepo.buscarLogado(usuario.getId());

        List<Nota> notasAluno = repository.listarNotasPorAluno(alunoId);

        if ((usuario.getRole() == UsuarioPapel.ALUNO) &&
                (logado.getId() == alunoId)) {
            if (notasAluno.isEmpty()) {
                logger.error("Aluno não possui notas cadastradas, Aluno ID: {}", alunoId);
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Você não possui notas cadastradas."
                );
            }
            logger.info("Retornando lista do Aluno de ID {}", alunoId);
            return notasAluno.stream().map(DtoNotaResponse::new).collect(Collectors.toList());
        }

        else if ((usuario.getRole() == UsuarioPapel.ALUNO) &&
                (usuario.getId() != alunoId)) {
            logger.error("Não autorizado. Aluno logado poade acessar apenas suas próprias notas");
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Notas disponíveis apenas ao usuário/aluno logado.");
        }

        if (notasAluno.isEmpty()) {
            logger.error("Não há notas para o Aluno de ID: {}", alunoId);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há notas cadastradas para este Aluno."
            );
        }
        logger.info("Retornando todas as notas do Aluno de ID {}.", alunoId);
        return notasAluno.stream().map(x -> new DtoNotaResponse(x)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public DtoNotaResponse criarNota(DtoNota nota) {
        if (alunoRepo.getById(nota.getAluno_id()).getUsuario().getRole() != UsuarioPapel.ALUNO) {
            logger.error("Uma NOTA só pode ser atribuída a um aluno (ROLE_ALUNO).");
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Uma nota só pode ser atribuída a um aluno (role: ALUNO).");
        }

        if (!alunoRepo.existsById(nota.getAluno_id())) {
            logger.error("Aluno não encontrado para se cadastrar uma nota. ID informado: {}", nota.getAluno_id());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado para se cadastrar uma nota."
            );
        }

        if (!materiaRepo.existsById(nota.getMateria_id())) {
            logger.error("Materia inexistente para se cadastrar uma nota");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "É necessário haver uma matéria para se cadastrar uma Nota."
            );
        }

        if (!docenteRepo.existsById(nota.getDocente_id())) {
            logger.error("Impossível cadastrar nota sem haver Professor.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Docente não encontrado para se cadastrar uma nota."
            );
        }

        Nota novaNota = new Nota();

        novaNota.setValor(nota.getValor());
        novaNota.setDataNota(nota.getDataNota());
        novaNota.setAluno(alunoRepo.getById(nota.getAluno_id()));
        novaNota.setDocente(docenteRepo.getById(nota.getDocente_id()));
        novaNota.setMateria(materiaRepo.getById(nota.getMateria_id()));
        repository.save(novaNota);
        logger.info("Nota cadastrada com sucesso.");
        return new DtoNotaResponse(novaNota);
    }

    @Override
    public DtoNotaResponse obterNotaPorId (Long id){
        if (!repository.existsById(id)) {
            logger.error("Nota não encontrada, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nota não encontrada."
            );
        }
        logger.info("Retornando nota de ID: {}", id);
        return new DtoNotaResponse(repository.findById(id).get());
        }

    @Override
    public DtoNotaResponse atualizarNota(Long id, DtoNota nota) {
        if (!repository.existsById(id)) {
            logger.error("Não foi encontrada a nota para PUT, ID: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nota não encontrada para edição."
            );
        }

        if (!materiaRepo.existsById(nota.getMateria_id())) {
            logger.error("A edição de uma nota exige que haja Matéria válida a ela atribuída. ID informado: {}", nota.getMateria_id());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Nota requer uma matéria."
            );
        }

        if (!alunoRepo.existsById(nota.getAluno_id())) {
            logger.error("Tentativa de se alterar nota para um Aluno inexistente. ID de Aluno info: {}", nota.getAluno_id());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Nota requer um Aluno cadastrado."
            );
        }

        if (!docenteRepo.existsById(nota.getDocente_id())) {
            logger.error("A alteração de uma Nota requer uma Docente cadastrado");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Nota requer uma Docente cadastrado."
            );
        }

        Nota atual = repository.findById(id).get();
        atual.setValor(nota.getValor());
        atual.setDataNota(nota.getDataNota());
        atual.setAluno(alunoRepo.getById(nota.getAluno_id()));
        atual.setDocente(docenteRepo.getById(nota.getDocente_id()));
        atual.setMateria(materiaRepo.getById(nota.getMateria_id()));

        logger.info("Nota alterada com sucesso.");
        return new DtoNotaResponse(repository.save(atual));
    }

    @Override
    public void deletarNota(Long id) {
        if (!repository.existsById(id)) {
            logger.error("Nota não encontrada para exlusão, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nota não encontrada para exlusão."
            );
        }
        Nota atual = repository.findById(id).get();
        repository.delete(atual);
        logger.info("Nota excluída com sucesso, ID {}", id);
    }
}
