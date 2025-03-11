package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> mostrarTodos();

    Cliente obtenerPorId(Long idCliente);

    Cliente crearCliente(Cliente cliente);

    Cliente actualizarCliente(Long idCliente, Cliente cliente);

    void eliminarCliente(Long idCliente);
}