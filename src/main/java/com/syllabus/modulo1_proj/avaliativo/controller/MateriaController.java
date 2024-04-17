package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoMateria;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import com.syllabus.modulo1_proj.avaliativo.service.MateriaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materias")
public class MateriaController {

    private final MateriaService service;

    public MateriaController(MateriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Materia> criarMateria(@RequestBody @Valid DtoMateria novaMateria) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarMateria(novaMateria));
    }

    @GetMapping("{id}")
    public ResponseEntity<Materia> obterMateriaPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterMateriaPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Materia> atualizarMateria(@PathVariable Long id, @RequestBody @Valid DtoMateria materia) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarMateria(id, materia));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarMateria(@PathVariable Long id) {
        service.deletarMateria(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
