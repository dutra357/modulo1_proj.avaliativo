package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioResponse;
import com.syllabus.modulo1_proj.avaliativo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cadastro")
public class CadastroController {

    private static final Logger logger = LoggerFactory.getLogger(CadastroController.class);

    private final UsuarioService service;

    public CadastroController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoUsuarioResponse> criarUsuario(@RequestBody @Valid DtoUsuarioRequest novoUsuario) {
        logger.info("Solicitado cadastramento de NOVO Usuário.");
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarUsuario(novoUsuario));
    }

}
