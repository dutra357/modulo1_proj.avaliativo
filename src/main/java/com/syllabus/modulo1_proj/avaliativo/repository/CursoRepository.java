package com.syllabus.modulo1_proj.avaliativo.repository;

import com.syllabus.modulo1_proj.avaliativo.entities.Curso;
import com.syllabus.modulo1_proj.avaliativo.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
