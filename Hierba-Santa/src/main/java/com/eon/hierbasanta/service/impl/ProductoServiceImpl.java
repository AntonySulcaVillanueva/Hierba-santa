package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.repository.ProductosRepository;
import com.eon.hierbasanta.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<Productos> mostrarTodas() {
        return productosRepository.findAll();
    }

    @Override
    public Productos optenerPorId(Long idproducto) {
        return productosRepository.findById(idproducto).orElse(null);
    }

    @Override
    public Productos crearProducto(Productos producto) {
        return productosRepository.save(producto);
    }

    @Override
    public Productos actualizarProducto(Long idproducto, Productos producto) {
        Productos productoActual = productosRepository.findById(idproducto).orElse(null);
        if (productoActual != null) {
            productoActual.setNombre(producto.getNombre());
            productoActual.setCategoria(producto.getCategoria());
            productoActual.setDescripcion(producto.getDescripcion());
            productoActual.setGramos(producto.getGramos());
            productoActual.setPrecio(producto.getPrecio());
            productoActual.setDescuento(producto.getDescuento());
            productoActual.setCantidad(producto.getCantidad());
            productoActual.setImagen1(producto.getImagen1());
            productoActual.setImagen2(producto.getImagen2());
            productoActual.setImagen3(producto.getImagen3());
            return productosRepository.save(productoActual);
        }
        return null;
    }

    @Override
    public void eliminarProducto(Long idproducto) {
        productosRepository.deleteById(idproducto);
    }
}
