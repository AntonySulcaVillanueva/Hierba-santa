package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.MovimientoInventarioProductos;
import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.repository.EmpleadoRepository;
import com.eon.hierbasanta.repository.MovimientoInventarioProductosRepository;
import com.eon.hierbasanta.repository.ProductosRepository;
import com.eon.hierbasanta.service.MovimientoInventarioProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoInventarioProductosServiceImpl implements MovimientoInventarioProductosService {

    @Autowired
    private MovimientoInventarioProductosRepository movimientoRepository;

    @Autowired
    private ProductosRepository productosRepository;



    @Override
    public List<MovimientoInventarioProductos> obtenerTodosLosMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public void eliminarMovimiento(Integer id) {
        movimientoRepository.deleteById(id);
    }

    @Override
    public MovimientoInventarioProductos registrarIngreso(MovimientoInventarioProductos movimiento) {
        Productos producto = productosRepository.findById(movimiento.getProducto().getIdproducto()).orElse(null);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        int cantidadCero=0 ;
        if (movimiento.getCantidad() > cantidadCero) {
            producto.setCantidad(producto.getCantidad() + movimiento.getCantidad());
            productosRepository.save(producto);
            movimiento.setTipoMovimiento("ingreso"); // Indicar que es un ingreso
            movimiento.setFechaMovimiento(LocalDateTime.now()); // Establecer la fecha de movimiento automáticamente
            return movimientoRepository.save(movimiento);
        } else {
            throw new RuntimeException("La cantidad para un ingreso debe ser mayor que cero.");
        }
    }

    @Override
    public MovimientoInventarioProductos registrarSalida(MovimientoInventarioProductos movimiento) {
        Productos producto = productosRepository.findById(movimiento.getProducto().getIdproducto()).orElse(null);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        if (producto.getCantidad() >= movimiento.getCantidad()) {
            producto.setCantidad(producto.getCantidad() - movimiento.getCantidad());
            productosRepository.save(producto);
            movimiento.setTipoMovimiento("salida"); // Indicar que es una salida
            movimiento.setFechaMovimiento(LocalDateTime.now()); // Establecer la fecha de movimiento automáticamente
            return movimientoRepository.save(movimiento);
        } else {
            throw new RuntimeException("Cantidad insuficiente en inventario.");
        }
    }
}