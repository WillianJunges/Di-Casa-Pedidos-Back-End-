package com.dicasa.pedidos.repository;

import com.dicasa.pedidos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.credenciais.usuario = :usuario")
    Optional<Usuario> findByCredenciaisUsuario(@Param("usuario") String usuario);

    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.credenciais.usuario = :usuario")
    boolean existsByCredenciaisUsuario(@Param("usuario") String usuario);

    boolean existsByEmail(String email);
}
