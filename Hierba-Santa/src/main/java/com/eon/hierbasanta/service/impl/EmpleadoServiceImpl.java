package com.eon.hierbasanta.service.impl;

import com.eon.hierbasanta.model.Empleado;
import com.eon.hierbasanta.repository.EmpleadoRepository;
import com.eon.hierbasanta.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> mostrarTodo() {
         return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarPorId(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado).orElse(null);
    }

    @Override
    public Empleado insertarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado, Long idEmpleado) {
        Empleado empleadoActual = empleadoRepository.findById(idEmpleado).orElse(null);
        if (empleadoActual!=null) {
            empleadoActual.setNombreCompleto(empleado.getNombreCompleto());
            empleadoActual.setDni(empleado.getDni());
            empleadoActual.setCargo(empleado.getCargo());
            empleadoActual.setTelefono(empleado.getTelefono());
            empleadoActual.setDireccion(empleado.getDireccion());
            empleadoActual.setFechaDeContrato(empleado.getFechaDeContrato());
            empleadoActual.setSalario(empleado.getSalario());
            return empleadoRepository.save(empleadoActual);

        }
        return null;
    }

    @Override
    public void eliminarEmpleado(Long idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
    }
}
