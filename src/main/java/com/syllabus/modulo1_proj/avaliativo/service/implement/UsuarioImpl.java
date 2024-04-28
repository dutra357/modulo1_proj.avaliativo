package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.login.LoginDtoRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import com.syllabus.modulo1_proj.avaliativo.repository.UsuarioRepository;
import com.syllabus.modulo1_proj.avaliativo.entities.enuns.UsuarioPapel;
import com.syllabus.modulo1_proj.avaliativo.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioImpl.class);

    private final UsuarioRepository repository;
    public UsuarioImpl(UsuarioRepository repository) {
        this.repository = repository;
    }


    @Override
    public DtoUsuarioResponse criarUsuario(DtoUsuarioRequest usuario) {

        if (repository.findByLogin(usuario.getLogin()) != null){
            log.error("Usuário já cadastrado");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Usuário já cadastrado!."
            );
        }

        conferePapel(usuario.getPapel());

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuario.getSenha());

        Usuario novoUsuario = new Usuario(usuario.getPapel(), senhaEncriptada,
                usuario.getLogin());

        UsuarioPapel papel = UsuarioPapel.valueOf(usuario.getPapel());

        novoUsuario.setRole(papel);

        repository.save(novoUsuario);
        log.info("Usuário criado");

        return new DtoUsuarioResponse(novoUsuario);
    }

    public Usuario buscarPorLogin(String usuario_login) {
        log.info("Buscando usuário por login");
        return repository.buscarPorLogin(usuario_login);
    }

    public boolean conferePapel(String papel) {
        var papelUpper = papel.toUpperCase();
        if(papelUpper.equals("ADMIN") || papelUpper.equals("PEDAGOGICO") ||
                papelUpper.equals("RECRUITER")||
                papelUpper.equals("PROFESSOR") ||
                papelUpper.equals("ALUNO")){
            return true;
        } log.error("Papel não condizente com as opções disponíveis (nível de acesso).");
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Papel não condizente com as opções disponíveis (nível de acesso).");
    }
}