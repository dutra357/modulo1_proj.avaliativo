package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNota;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNotaResponse;
import com.syllabus.modulo1_proj.avaliativo.service.NotaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notas")
public class NotaController {

    private static final Logger logger = LoggerFactory.getLogger(NotaController.class);

    private final NotaService service;

    public NotaController(NotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoNotaResponse> criarNota(@RequestBody @Valid DtoNota novaNota) {
        logger.info("Soliciada inserção de nova Nota.");
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarNota(novaNota));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoNotaResponse> obterNotaPorId(@PathVariable Long id) {
        logger.info("Soliciada Nota por ID, {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.obterNotaPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoNotaResponse> atualizarNota(@PathVariable Long id, @RequestBody @Valid DtoNota nota) {
        logger.info("Soliciada alteração de Nota, ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarNota(id, nota));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarNota(@PathVariable Long id) {
        logger.info("Soliciada exclusão de Nota, ID {}.", id);
        service.deletarNota(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
