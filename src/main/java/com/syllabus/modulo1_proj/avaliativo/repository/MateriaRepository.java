package com.syllabus.modulo1_proj.avaliativo.repository;

import com.syllabus.modulo1_proj.avaliativo.entities.Docente;
import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
