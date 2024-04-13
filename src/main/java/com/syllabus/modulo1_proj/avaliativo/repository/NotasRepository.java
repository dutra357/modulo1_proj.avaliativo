package com.syllabus.modulo1_proj.avaliativo.repository;

import com.syllabus.modulo1_proj.avaliativo.entities.Materia;
import com.syllabus.modulo1_proj.avaliativo.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotasRepository extends JpaRepository<Nota, Long> {
}
