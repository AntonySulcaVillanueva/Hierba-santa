package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.Cliente;
import com.eon.hierbasanta.repository.ClienteRepository;
import com.eon.hierbasanta.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public List<Cliente> mostrarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obtenerPorId(Long idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Long idCliente, Cliente cliente) {
        Cliente clienteActual = clienteRepository.findById(idCliente).orElse(null);
        if (clienteActual != null) {
            clienteActual.setNombreCompleto(cliente.getNombreCompleto());
            clienteActual.setDni(cliente.getDni());
            clienteActual.setCorreo(cliente.getCorreo());
            clienteActual.setTelefono(cliente.getTelefono());
            clienteActual.setDireccion(cliente.getDireccion());
            clienteActual.setFechaNacimiento(cliente.getFechaNacimiento());
            clienteActual.setTipoCliente(cliente.getTipoCliente());
            clienteActual.setTotalPedidosSinCancelar(cliente.getTotalPedidosSinCancelar());
            return clienteRepository.save(clienteActual);
        }
        return null;
    }

    @Override
    public void eliminarCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }
}