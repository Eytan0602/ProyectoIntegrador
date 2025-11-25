package com.pnp.portal.repository;

import com.pnp.portal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);
}
