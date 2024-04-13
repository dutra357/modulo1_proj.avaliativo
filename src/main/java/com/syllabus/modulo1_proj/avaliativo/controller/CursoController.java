package com.syllabus.modulo1_proj.avaliativo.controller;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoCurso;
import com.syllabus.modulo1_proj.avaliativo.entities.Curso;
import com.syllabus.modulo1_proj.avaliativo.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Curso> adicionarCurso(@RequestBody @Valid DtoCurso novoCurso) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarCurso(novoCurso));
    }

    @GetMapping("{id}")
    public ResponseEntity<Curso> obterCursoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterCursoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarCursos());
    }

    @PutMapping("{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody @Valid DtoCurso curso) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarCurso(id, curso));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        service.deletarCurso(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
