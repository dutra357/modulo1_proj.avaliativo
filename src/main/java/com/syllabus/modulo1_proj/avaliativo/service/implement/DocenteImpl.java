package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Docente;
import com.syllabus.modulo1_proj.avaliativo.repository.*;
import com.syllabus.modulo1_proj.avaliativo.service.DocenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteImpl implements DocenteService {

    private static final Logger logger = LoggerFactory.getLogger(DocenteImpl.class);

    private final DocenteRepository repository;
    private final UsuarioRepository usuarioRepo;
    public DocenteImpl(DocenteRepository repository, UsuarioRepository usuarioRepo) {
        this.repository = repository;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public DtoDocenteResponse criarDocente(DtoDocenteRequest docente) {

        if (!usuarioRepo.existsById(docente.getUsuario_id())){
            logger.error("Para cadastrar um docente é preciso ter um usuário anterior, ao qual é atribuída tal qualidade.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Impossível criar um docente para um usuário não cadastrado."
            );
        }
        Docente novoDocente = new Docente();

        novoDocente.setNome(docente.getNome());
        novoDocente.setDataEntrada(LocalDate.now());
        novoDocente.setUsuario(usuarioRepo.getById(docente.getUsuario_id()));

        repository.save(novoDocente);
        logger.info("Novo docente cadastrado com sucesso.");
        return new DtoDocenteResponse(novoDocente);
    }

    @Override
    public DtoDocenteResponse obterDocentePorId (Long id){
        if (!repository.existsById(id)) {
            logger.error("Não encontrado Docente com o ID informado, {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Docente não encontrado."
            );
        }
        logger.info("Retornando o Docente pesquisado por ID, {}", id);
        return new DtoDocenteResponse(repository.findById(id).get());
    }

    @Override
    public DtoDocenteResponse atualizarDocente(Long id, DtoDocenteRequest docente) {
        if (!repository.existsById(id)) {
            logger.error("Docente não encontrada para alteração");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Docente não encontrada para alteração."
            );
        }

        Docente atual = repository.findById(id).get();
        atual.setNome(docente.getNome());
        atual.setDataEntrada(LocalDate.now());
        repository.save(atual);
        logger.info("Docente editado com sucesso, ID {}", id);

        return new DtoDocenteResponse(atual);
    }

    @Override
    public void deletarDocente(Long id) {
        if (!repository.existsById(id)) {
                logger.error("Não encontrado Docente a ser excluído, ID {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Docente não encontrado."
            );
        }
        Docente atual = repository.findById(id).get();
        repository.delete(atual);
        logger.info("Docente excluído com sucesso, ID {}", id);
    }

    @Override
    public List<DtoDocenteResponse> listarTodosDocentes() {

        List<Docente> listaDocentes = repository.findAll();

        if (listaDocentes.isEmpty()) {
            logger.error("Não há Docentes a serem listados. Lista vazia.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Não há Docentes cadastrados."
            );
        }
        logger.info("Retornando lista de todos os Docentes cadastrados.");
        return listaDocentes.stream().map(x -> new DtoDocenteResponse(x)).collect(Collectors.toList());
    }
}
