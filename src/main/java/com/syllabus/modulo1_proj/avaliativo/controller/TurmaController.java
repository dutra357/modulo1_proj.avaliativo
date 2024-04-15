package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma.DtoTurma;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.turma.DtoTurmaResponse;
import com.syllabus.modulo1_proj.avaliativo.service.TurmaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turmas")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoTurmaResponse> criarTurma(@RequestBody DtoTurma novaTurma) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarTurma(novaTurma));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoTurmaResponse> obterTurmaPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTurmaPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoTurmaResponse> atualizarTurma(@PathVariable Long id, @RequestBody @Valid DtoTurma turma) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarTurma(id, turma));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        service.deletarTurma(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id_curso}/curso")
    public ResponseEntity<List<DtoTurmaResponse>> listarTodasAsTurmas() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodasAsTurmas());
    }
}
