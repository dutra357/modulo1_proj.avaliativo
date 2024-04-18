package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNota;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNotaResponse;
import com.syllabus.modulo1_proj.avaliativo.service.NotaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notas")
public class NotaController {

    private final NotaService service;

    public NotaController(NotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoNotaResponse> criarNota(@RequestBody @Valid DtoNota novaNota) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarNota(novaNota));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoNotaResponse> obterNotaPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterNotaPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoNotaResponse> atualizarNota(@PathVariable Long id, @RequestBody @Valid DtoNota nota) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarNota(id, nota));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarNota(@PathVariable Long id) {
        service.deletarNota(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
