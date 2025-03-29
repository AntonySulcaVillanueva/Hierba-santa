package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.Insumos;
import com.eon.hierbasanta.model.MovimientoInventarioInsumos;
import com.eon.hierbasanta.repository.EmpleadoRepository;
import com.eon.hierbasanta.repository.InsumosRepository;
import com.eon.hierbasanta.repository.MovimientoInventarioInsumosRepository;
import com.eon.hierbasanta.service.MovimientoInventarioInsumosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoInventarioInsumosServiceImpl implements MovimientoInventarioInsumosService {

    @Autowired
    private MovimientoInventarioInsumosRepository movimientoInsumosRepository;

    @Autowired
    private InsumosRepository insumosRepository;

    @Override
    public List<MovimientoInventarioInsumos> obtenerTodosLosMovimientos() {
        return movimientoInsumosRepository.findAll();
    }

    @Override
    public void eliminarMovimiento(Integer id) {
        movimientoInsumosRepository.deleteById(id);
    }

    @Override
    public MovimientoInventarioInsumos registrarIngreso(MovimientoInventarioInsumos movimiento) {
        Insumos insumo = insumosRepository.findById(movimiento.getInsumo().getIdInsumo()).orElse(null);
        if (insumo == null) {
            throw new RuntimeException("Insumo no encontrado");
        }

        if (movimiento.getCantidad() > 0) {
            insumo.setCantidad(insumo.getCantidad() + movimiento.getCantidad());
            insumosRepository.save(insumo);
            movimiento.setTipoMovimiento("ingreso");
            movimiento.setFechaMovimiento(LocalDateTime.now());
            return movimientoInsumosRepository.save(movimiento);
        } else {
            throw new RuntimeException("La cantidad para un ingreso debe ser mayor que cero.");
        }
    }

    @Override
    public MovimientoInventarioInsumos registrarSalida(MovimientoInventarioInsumos movimiento) {
        Insumos insumo = insumosRepository.findById(movimiento.getInsumo().getIdInsumo()).orElse(null);
        if (insumo == null) {
            throw new RuntimeException("Insumo no encontrado");
        }
        boolean cantidadSuficiente = insumo.getCantidad() >= movimiento.getCantidad();
        if (cantidadSuficiente) {
            insumo.setCantidad(insumo.getCantidad() - movimiento.getCantidad());
            insumosRepository.save(insumo);
            movimiento.setTipoMovimiento("salida");
            movimiento.setFechaMovimiento(LocalDateTime.now());
            return movimientoInsumosRepository.save(movimiento);
        } else {
            throw new RuntimeException("Cantidad insuficiente en inventario.");
        }
    }
}