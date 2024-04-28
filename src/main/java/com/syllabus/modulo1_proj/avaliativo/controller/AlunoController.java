package com.syllabus.modulo1_proj.avaliativo.controller;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.DtoNotaFinal;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.aluno.DtoAlunoResponse;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.notas.DtoNotaResponse;
import com.syllabus.modulo1_proj.avaliativo.service.AlunoService;
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
@RequestMapping("/alunos")
public class AlunoController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);
    private final AlunoService service;
    private  final NotaService notaService;

    public AlunoController(AlunoService service, NotaService notaService) {
        this.service = service;
        this.notaService = notaService;
    }

    @PostMapping
    public ResponseEntity<DtoAlunoResponse> criarAluno(@RequestBody @Valid DtoAlunoRequest novoAluno) {
        logger.info("Solicitado o cadastramento de nono Aluno.");
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(service.criarAluno(novoAluno));
    }

    @GetMapping("{id}")
    public ResponseEntity<DtoAlunoResponse> obterAlunoPorId(@PathVariable Long id) {
        logger.info("Solicitado dados do Aluno de ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.obterAlunoPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DtoAlunoResponse> atualizarAluno(@PathVariable Long id, @RequestBody @Valid DtoAlunoRequest aluno) {
        logger.info("Solicitada alteração cadastral (PUT), Aluno ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarAluno(id, aluno));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        logger.info("Solicitada exclusão de Aluno (DELETE), ID {}.", id);
        service.deletarAluno(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<DtoAlunoResponse>> listarTodosAlunos() {
        logger.info("Solicitada listagem completa de alunos cadastrados no sistema.");
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodosAlunos());
    }

    //Endpoint apontado no projeto como de Notas
    @GetMapping("{id_aluno}/notas")
    public ResponseEntity<List<DtoNotaResponse>> listarNotasPorAluno(@PathVariable Long id_aluno) {
        logger.info("Solicitada listagem completa de Notas de Aluno, cadastro ID {}.", id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body(notaService.listarNotasPorAluno(id_aluno));
    }

    //Get pontuação total do aluno
    //"a pontuação é dada pela seguinte lógica: soma das notas, dividido pelo número de matérias, multiplicado por 10"
    @GetMapping("{id}/pontuacao")
    public ResponseEntity<DtoNotaFinal> pontuacaoAluno(@PathVariable Long id) {
        logger.info("Solicitada a pontuação TOTAL do Aluno, cadastro ID {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.pontuacaoAluno(id));
    }
}
