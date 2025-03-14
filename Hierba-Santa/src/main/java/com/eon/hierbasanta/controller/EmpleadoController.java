package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Empleado;
import com.eon.hierbasanta.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/listarEmpleados")
    public String listarEmpledo(Model model) {
        List<Empleado> empleados=empleadoService.mostrarTodo();
        model.addAttribute("llaveEmpleados", empleados);
        return "Empleado/listarEmpleados";
    }

    @GetMapping("/insertarEmpleado")
    public String insertarEmpleadoForm(Model model) {
        model.addAttribute("llaveEmpleado", new Empleado());
        return "Empleado/insertarEmpleado";
    }

    @PostMapping("/insertarEmpleado")
    public String insertarEmpleado(@ModelAttribute Empleado empleado, @RequestParam("fechaDeContrato") String fechaDeContratoStr) {
        LocalDate fechaDeContrato = LocalDate.parse(fechaDeContratoStr);
        empleado.setFechaDeContrato(fechaDeContrato);
        empleadoService.insertarEmpleado(empleado);
        return "redirect:/empleado/listarEmpleados";
    }


    @GetMapping("/detalleEmpleado/{id}")
    public String mostrarEmpleado(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.buscarPorId(id);
        model.addAttribute("llaveEmpleados", empleado);
        return "Empleado/detalleEmpleado";
    }

    @GetMapping("/eliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return "redirect:/empleado/listarEmpleados";
    }
}
