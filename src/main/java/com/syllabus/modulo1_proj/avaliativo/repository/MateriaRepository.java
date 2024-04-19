package com.syllabus.modulo1_proj.avaliativo.repository;
import com.syllabus.modulo1_proj.avaliativo.entities.Curso;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query("SELECT mat FROM Materia mat WHERE mat.curso.id = :curso_id")
    List<Materia> listarMateriaPorCurso(@Param("curso_id") Long curso_id);

    @Query("SELECT curso FROM Curso curso WHERE curso.id = :curso_id")
    Curso buscaCurso(@Param("curso_id") Long curso_id);

}
