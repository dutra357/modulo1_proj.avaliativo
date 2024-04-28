package com.syllabus.modulo1_proj.avaliativo.repository;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotasRepository extends JpaRepository<Nota, Long> {

    @Query("SELECT nota FROM Nota nota WHERE nota.aluno.id = :aluno_id")
    List<Nota> listarNotasPorAluno(@Param("aluno_id") Long aluno_id);

}
