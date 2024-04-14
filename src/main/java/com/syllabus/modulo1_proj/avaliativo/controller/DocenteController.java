package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.docente.DtoDocenteResponse;
import com.syllabus.modulo1_proj.avaliativo.service.DocenteService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("docentes")
public class DocenteController {

    private final DocenteService service;

    public DocenteController(DocenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoDocenteResponse> criarDocente(@RequestBody @Valid DtoDocenteRequest novoDocente) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarDocente(novoDocente));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoDocenteResponse> obterDocentePorId(@PathVariable Long docenteId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterDocentePorId(docenteId));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoDocenteResponse> atualizarDocente(@PathVariable Long id, @RequestBody @Valid DtoDocenteRequest docente) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarDocente(id, docente));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarDocente(@PathVariable Long id) {
        service.deletarDocente(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<DtoDocenteResponse>> listarTodosDocentes() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodosDocentes());
    }
}
