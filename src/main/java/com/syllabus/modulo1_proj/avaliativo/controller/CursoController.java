package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import com.syllabus.modulo1_proj.avaliativo.service.MateriaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoCurso;
import com.syllabus.modulo1_proj.avaliativo.entities.Curso;
import com.syllabus.modulo1_proj.avaliativo.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private static final Logger logger = LoggerFactory.getLogger(CursoController.class);
    private final CursoService service;
    private final MateriaService materiaService;

    public CursoController(CursoService service, MateriaService materiaService) {
        this.service = service;
        this.materiaService = materiaService;
    }

    @PostMapping
    public ResponseEntity<Curso> adicionarCurso(@RequestBody @Valid DtoCurso novoCurso) {
        logger.info("Solicitado cadastro de novo Curso.");
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarCurso(novoCurso));
    }

    @GetMapping("{id}")
    public ResponseEntity<Curso> obterCursoPorId(@PathVariable Long id) {
        logger.info("Solicitado dados do Curso ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.obterCursoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        logger.info("Solicitada listagem completa de Cursos cadastrados no sistema.");
        return ResponseEntity.status(HttpStatus.OK).body(service.listarCursos());
    }

    @PutMapping("{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody @Valid DtoCurso curso) {
        logger.info("Solicitada alteração de Curso (PUT), ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarCurso(id, curso));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        logger.info("Solicitada exclusão de Curso, ID {}.", id);
        service.deletarCurso(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    //Listar matérias por curso (consta como endpoint de MATERIAS no projeto)
    @GetMapping("{id_curso}/materias")
    public ResponseEntity<List<Materia>> listarMateriaPorCurso(@PathVariable Long id_curso) {
        logger.info("Solicitada a listagem de Matéiras alocadas em um curso, de ID {}.", id_curso);
        return ResponseEntity.status(HttpStatus.OK).body(materiaService.listarMateriaPorCurso(id_curso));
    }
}
