package com.eon.hierbasanta.repository;

import com.eon.hierbasanta.model.MovimientoInventarioInsumos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoInventarioInsumosRepository extends JpaRepository<MovimientoInventarioInsumos, Integer> {
}
