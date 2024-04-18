package com.syllabus.modulo1_proj.avaliativo.repository;
import com.syllabus.modulo1_proj.avaliativo.entities.Aluno;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT nota FROM Nota nota WHERE nota.aluno.id = :aluno_id")
    List<Nota> listarNotasPorAluno(@Param("aluno_id") Long aluno_id);

    @Query("SELECT aluno FROM Aluno aluno WHERE aluno.usuario.id = :usuario_id")
    Aluno buscarLogado(@Param("usuario_id") Long usuario_id);

}
