package com.eon.hierbasanta.repository;

import com.eon.hierbasanta.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriasRepositoy extends JpaRepository<Categorias, Integer> {
}
