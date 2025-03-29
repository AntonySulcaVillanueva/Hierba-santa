package com.eon.hierbasanta.repository;

import com.eon.hierbasanta.model.MovimientoInventarioProductos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoInventarioProductosRepository extends JpaRepository<MovimientoInventarioProductos, Integer> {
}