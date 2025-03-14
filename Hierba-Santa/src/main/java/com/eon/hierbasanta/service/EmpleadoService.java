package com.eon.hierbasanta.service;

import com.eon.hierbasanta.model.Empleado;

import java.util.List;

public interface EmpleadoService {

    List<Empleado> mostrarTodo();

    Empleado buscarPorId(Long idEmpleado);

    Empleado insertarEmpleado(Empleado empleado);

    Empleado actualizarEmpleado(Empleado empleado, Long idEmpleado);

    void eliminarEmpleado(Long idEmpleado);


}
