package com.eon.hierbasanta.repository;

import com.eon.hierbasanta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUsernameAndPassword(String username, String password);
}