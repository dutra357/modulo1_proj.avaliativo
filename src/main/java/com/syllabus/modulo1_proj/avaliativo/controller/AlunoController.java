package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoNotaFinal;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoResponse;
import com.syllabus.modulo1_proj.avaliativo.service.AlunoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DtoAlunoResponse> criarAluno(@RequestBody @Valid DtoAlunoRequest novoAluno) {
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarAluno(novoAluno));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoAlunoResponse> obterAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterAlunoPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoAlunoResponse> atualizarAluno(@PathVariable Long id, @RequestBody @Valid DtoAlunoRequest aluno) {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarAluno(id, aluno));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        service.deletarAluno(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<DtoAlunoResponse>> listarTodosAlunos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodosAlunos());
    }



    @GetMapping("{id}/pontuacao")
    public ResponseEntity<DtoNotaFinal> pontuacaoAluno(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.pontuacaoAluno(id));
    }
}
