package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteResponse;
import com.syllabus.modulo1_proj.avaliativo.service.DocenteService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("docentes")
public class DocenteController {

    private final DocenteService service;

    private static final Logger logger = LoggerFactory.getLogger(DocenteController.class);

    public DocenteController(DocenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoDocenteResponse> criarDocente(@RequestBody @Valid DtoDocenteRequest novoDocente) {
        logger.info("Solicitado cadastro de novo Docente.");
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarDocente(novoDocente));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoDocenteResponse> obterDocentePorId(@PathVariable Long id) {
        logger.info("Solicitado cadastro de Docente por ID, {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.obterDocentePorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoDocenteResponse> atualizarDocente(@PathVariable Long id, @RequestBody @Valid DtoDocenteRequest docente) {
        logger.info("Solicitado PUT de Docente, ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarDocente(id, docente));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarDocente(@PathVariable Long id) {
        logger.info("Solicitada exclusão de cadastro de Docente, ID {}.", id);
        service.deletarDocente(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<DtoDocenteResponse>> listarTodosDocentes() {
        logger.info("Solicitada listagem completa de Docentes cadastrados no sistema.");
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodosDocentes());
    }
}
