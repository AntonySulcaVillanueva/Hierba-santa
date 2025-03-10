package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.Categorias;
import com.eon.hierbasanta.repository.CategoriasRepositoy;
import com.eon.hierbasanta.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl  implements CategoriaService {

    @Autowired
    private CategoriasRepositoy categoriasRepositoy;

    @Override
    public List<Categorias> mostrarTodas() {
        return categoriasRepositoy.findAll();
    }

    @Override
    public Categorias optenerPorId(Long idcategoria) {
        return categoriasRepositoy.findById(idcategoria).orElse(null);
    }

    @Override
    public Categorias crearCategoria(Categorias categoria) {
        return categoriasRepositoy.save(categoria);
    }

    @Override
    public Categorias actualizarCategoria(Long idcategoria, Categorias categoria) {
        Categorias categoriaActual = categoriasRepositoy.findById(idcategoria).orElse(null);
        if (categoriaActual != null) {
            categoriaActual.setNombre(categoria.getNombre());
            categoriaActual.setDescripcion(categoria.getDescripcion());
            return categoriasRepositoy.save(categoriaActual);
        }
        return null;
    }

    @Override
    public void eliminarCategoria(Long idcategoria) {
        categoriasRepositoy.deleteById(idcategoria);

    }

}
