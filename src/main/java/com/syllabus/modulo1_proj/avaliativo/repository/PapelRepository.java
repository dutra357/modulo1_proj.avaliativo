package com.syllabus.modulo1_proj.avaliativo.repository;
import com.syllabus.modulo1_proj.avaliativo.entities.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
}
