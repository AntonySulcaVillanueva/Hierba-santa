package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.MovimientoInventarioProductos;
import java.util.List;

public interface MovimientoInventarioProductosService {

    List<MovimientoInventarioProductos> obtenerTodosLosMovimientos();
    void eliminarMovimiento(Integer id);
    MovimientoInventarioProductos registrarIngreso(MovimientoInventarioProductos movimiento);
    MovimientoInventarioProductos registrarSalida(MovimientoInventarioProductos movimiento);
}