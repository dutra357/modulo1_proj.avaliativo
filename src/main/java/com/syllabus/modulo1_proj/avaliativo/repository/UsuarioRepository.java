package com.syllabus.modulo1_proj.avaliativo.repository;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
