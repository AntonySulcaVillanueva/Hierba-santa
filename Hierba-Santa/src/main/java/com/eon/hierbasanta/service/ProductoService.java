package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.Productos;

import java.util.List;

public interface ProductoService {

    List<Productos> mostrarTodas();

    Productos optenerPorId(Long idproducto);

    Productos crearProducto(Productos producto);

    Productos actualizarProducto(Long idproducto, Productos producto);

    void eliminarProducto(Long idproducto);
}
