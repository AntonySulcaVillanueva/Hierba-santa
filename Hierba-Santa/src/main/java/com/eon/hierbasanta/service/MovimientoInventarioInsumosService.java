package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.MovimientoInventarioInsumos;
import java.util.List;

public interface MovimientoInventarioInsumosService {

    List<MovimientoInventarioInsumos> obtenerTodosLosMovimientos();
    void eliminarMovimiento(Integer id);
    MovimientoInventarioInsumos registrarIngreso(MovimientoInventarioInsumos movimiento);
    MovimientoInventarioInsumos registrarSalida(MovimientoInventarioInsumos movimiento);
}