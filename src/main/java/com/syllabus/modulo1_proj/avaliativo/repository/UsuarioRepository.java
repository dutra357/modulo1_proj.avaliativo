package com.syllabus.modulo1_proj.avaliativo.repository;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);

    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.login = :usuario_login")
    Usuario buscarPorLogin(@Param("usuario_login") String usuario_login);
}
