package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.Categorias;

import java.util.List;

public interface CategoriaService {

    List<Categorias> mostrarTodas();

    Categorias optenerPorId(Long idcategoria);

    Categorias crearCategoria(Categorias categoria);

    Categorias actualizarCategoria(Long idcategoria, Categorias categoria);

    void eliminarCategoria(Long idcategoria);

}


