package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.TipoCliente;

import java.util.List;

public interface TipoClienteService {

    List<TipoCliente> mostrarTodos();

    TipoCliente optenerPorId(Long idTipoCliente);

    TipoCliente crearTipoCliente(TipoCliente tipoCliente);

    TipoCliente actualizarTipoCliente(Long idTipoCliente, TipoCliente tipoCliente);

    void eliminarTipoCliente(Long idTipoCliente);
}
