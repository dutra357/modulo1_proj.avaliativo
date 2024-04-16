package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Papel;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import com.syllabus.modulo1_proj.avaliativo.repository.UsuarioRepository;
import com.syllabus.modulo1_proj.avaliativo.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioImpl implements UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioImpl.class);

    private final UsuarioRepository repository;
    public UsuarioImpl(UsuarioRepository repository) {
        this.repository = repository;
    }


    @Override
    public DtoUsuarioResponse criarUsuario(DtoUsuarioRequest usuario) {
        if (repository.findByLogin(usuario.getLogin()) != null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Usuário já cadastrado!."
            );
        }

        if (!conferePapel(usuario.getPapel())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "PAPEL inválido!."
            );
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuario.getSenha());

        Usuario novoUsuario = new Usuario(senhaEncriptada, usuario.getLogin(),
                usuario.getPapel());

        repository.save(novoUsuario);

        return new DtoUsuarioResponse(novoUsuario);
    }

    public boolean conferePapel(String papel) {
        var papelUpper = papel.toUpperCase();
        if(papelUpper == "ADM" || papelUpper == "PEDAGOGICO" ||
                papelUpper == "RECRUITER" ||
                papelUpper == "PROFESSOR" ||
                papelUpper == "ALUNO"){
            return true;
        } return false;

    }

}