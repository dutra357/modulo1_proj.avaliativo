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
import com.syllabus.modulo1_proj.avaliativo.service.UsuarioService;
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
                    UsuarioService usuarioService, UsuarioImpl usuarioService1,
                    HttpServletRequest request) {
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
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }

        String usuarioSessao = request.getUserPrincipal().getName();
        Usuario usuario = usuarioService.buscarPorLogin(usuarioSessao);
        Aluno logado = alunoRepo.buscarLogado(usuario.getId());

        List<Nota> notasAluno = repository.listarNotasPorAluno(alunoId);

        if ((usuario.getRole() == UsuarioPapel.ALUNO) &&
                (logado.getId() == alunoId)) {
            if (notasAluno.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Você não possui notas cadastradas."
                );
            }
            return notasAluno.stream().map(x -> new DtoNotaResponse(x)).collect(Collectors.toList());
        }

        else if ((usuario.getRole() == UsuarioPapel.ALUNO) &&
                (usuario.getId() != alunoId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Notas disponíveis apenas ao usuário/aluno logado.");
        }

        if (notasAluno.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há notas cadastradas para este Aluno."
            );
        }
        return notasAluno.stream().map(x -> new DtoNotaResponse(x)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public DtoNotaResponse criarNota(DtoNota nota) {
        if (!materiaRepo.existsById(nota.getMateria_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia não encontrada para se cadastrar uma nota."
            );
        }

        if (!alunoRepo.existsById(nota.getAluno_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado para se cadastrar uma nota."
            );
        }

        if (!docenteRepo.existsById(nota.getDocente_id())) {
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
        return new DtoNotaResponse(novaNota);
    }

    @Override
    public DtoNotaResponse obterNotaPorId (Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nota não encontrada."
            );
        }
        return new DtoNotaResponse(repository.findById(id).get());
        }

    @Override
    public DtoNotaResponse atualizarNota(Long id, DtoNota nota) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nota não encontrada para edição."
            );
        }

        if (!materiaRepo.existsById(nota.getMateria_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Nota requer uma matéria."
            );
        }

        if (!alunoRepo.existsById(nota.getAluno_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A alteração de uma Nota requer um Aluno cadastrado."
            );
        }

        if (!docenteRepo.existsById(nota.getDocente_id())) {
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

        return new DtoNotaResponse(repository.save(atual));
    }

    @Override
    public void deletarNota(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nota não encontrada."
            );
        }
        Nota atual = repository.findById(id).get();
        repository.delete(atual);
    }
}
