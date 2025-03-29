package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> findAll();
    Optional<Pedido> findById(Integer id);
    Pedido save(Pedido pedido);
    void deleteById(Integer id);
}