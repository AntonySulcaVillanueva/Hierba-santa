package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.repository.TipoClienteRepository;
import com.eon.hierbasanta.service.TipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoClienteImpl implements TipoClienteService {

    @Autowired
    private TipoClienteRepository tipoClienteRepository;


    @Override
    public List<TipoCliente> mostrarTodos() {
        return tipoClienteRepository.findAll();
    }

    @Override
    public TipoCliente optenerPorId(Long idTipoCliente) {
        return tipoClienteRepository.findById(idTipoCliente).orElse(null);
    }

    @Override
    public TipoCliente crearTipoCliente(TipoCliente tipoCliente) {
        return tipoClienteRepository.save(tipoCliente);
    }

    @Override
    public TipoCliente actualizarTipoCliente(Long idTipoCliente, TipoCliente tipoCliente) {
        TipoCliente tipoClienteActual = tipoClienteRepository.findById(idTipoCliente).orElse(null);
        if (tipoClienteActual!=null) {
            tipoClienteActual.setTipoCliente(tipoCliente.getTipoCliente());
            tipoClienteActual.setTipoCliente(tipoCliente.getTipoCliente());
            tipoClienteActual.setDescripcion(tipoCliente.getDescripcion());
            return tipoClienteRepository.save(tipoClienteActual);
        }
        return null;
    }

    @Override
    public void eliminarTipoCliente(Long idTipoCliente) {
        tipoClienteRepository.deleteById(idTipoCliente);

    }
}
