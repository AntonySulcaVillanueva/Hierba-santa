package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Empleado;
import com.eon.hierbasanta.model.Insumos;
import com.eon.hierbasanta.model.MovimientoInventarioInsumos;
import com.eon.hierbasanta.repository.EmpleadoRepository;
import com.eon.hierbasanta.repository.InsumosRepository;
import com.eon.hierbasanta.service.MovimientoInventarioInsumosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/invInsumo")
public class MovimientoInventarioInsumosController {

    @Autowired
    private InsumosRepository insumosRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private MovimientoInventarioInsumosService movimientoService;

    @GetMapping("/listar")
    public String listarMovimientos(Model model) {
        List<MovimientoInventarioInsumos> movimientos = movimientoService.obtenerTodosLosMovimientos();
        model.addAttribute("movimientos", movimientos);
        return "InvInsumo/listaMovimientos";
    }

    @GetMapping("/ingreso-form")
    public String mostrarFormularioIngreso(Model model) {
        List<Insumos> insumos = insumosRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();
        model.addAttribute("movimiento", new MovimientoInventarioInsumos());
        model.addAttribute("insumos", insumos);
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Registrar Ingreso de Insumo");
        return "InvInsumo/ingresoForm";
    }

    @PostMapping("/ingresoForm")
    public String registrarIngreso(@ModelAttribute("movimiento") MovimientoInventarioInsumos movimiento) {
        movimiento.setTipoMovimiento("ingreso"); // Establecer tipo de movimiento como ingreso
        movimientoService.registrarIngreso(movimiento);
        return "redirect:/invInsumo/listar";
    }

    @GetMapping("/salida-form")
    public String mostrarFormularioSalida(Model model) {
        List<Insumos> insumos = insumosRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();
        model.addAttribute("movimiento", new MovimientoInventarioInsumos());
        model.addAttribute("insumos", insumos);
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Registrar Salida de Insumo");
        return "InvInsumo/salidaForm";
    }

    @PostMapping("/salidaForm")
    public String registrarSalida(@ModelAttribute("movimiento") MovimientoInventarioInsumos movimiento) {
        movimiento.setTipoMovimiento("salida"); // Establecer tipo de movimiento como salida
        movimientoService.registrarSalida(movimiento);
        return "redirect:/invInsumo/listar";
    }

    @GetMapping("/eliminar/movimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id) {
        movimientoService.eliminarMovimiento(id);
        return "redirect:/invInsumo/listar";
    }
}