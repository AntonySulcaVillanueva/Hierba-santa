package com.eon.hierbasanta.repository;

import com.eon.hierbasanta.model.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Long> {
}
