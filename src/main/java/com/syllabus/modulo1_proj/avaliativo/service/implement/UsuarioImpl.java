package com.syllabus.modulo1_proj.avaliativo.service.implement;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioResponse;
import com.syllabus.modulo1_proj.avaliativo.entities.Papel;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import com.syllabus.modulo1_proj.avaliativo.repository.UsuarioRepository;
import com.syllabus.modulo1_proj.avaliativo.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UsuarioImpl implements UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioImpl.class);

    private final UsuarioRepository repository;
    public UsuarioImpl(UsuarioRepository repository) {
        this.repository = repository;
    }


    @Override
    public DtoUsuarioResponse criarUsuario(DtoUsuarioRequest usuario) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(usuario.getLogin());
        novoUsuario.setSenha(usuario.getSenha());
        novoUsuario.setPapel(new Papel(usuario.getPapel()));
        repository.save(novoUsuario);

        return new DtoUsuarioResponse(novoUsuario);
    }

}