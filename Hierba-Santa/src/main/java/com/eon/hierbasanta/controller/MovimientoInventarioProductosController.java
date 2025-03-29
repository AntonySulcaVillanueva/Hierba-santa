package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Empleado;
import com.eon.hierbasanta.model.MovimientoInventarioProductos;
import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.repository.EmpleadoRepository;
import com.eon.hierbasanta.repository.ProductosRepository;
import com.eon.hierbasanta.service.MovimientoInventarioProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/invProducto")
public class MovimientoInventarioProductosController {

    @Autowired
    private ProductosRepository productoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private MovimientoInventarioProductosService movimientoService;

    @GetMapping("/listar")
    public String listarMovimientos(Model model) {
        List<MovimientoInventarioProductos> movimientos = movimientoService.obtenerTodosLosMovimientos();
        model.addAttribute("movimientos", movimientos);
        return "InvProducto/listaMovimientos";
    }

    @GetMapping("/ingreso-form")
    public String mostrarFormularioIngreso(Model model) {
        List<Productos> productos = productoRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();
        model.addAttribute("movimiento", new MovimientoInventarioProductos());
        model.addAttribute("productos", productos);
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Registrar Ingreso de Producto");
        return "InvProducto/ingresoForm";
    }

    @PostMapping("/ingresoForm")
    public String registrarIngreso(@ModelAttribute("movimiento") MovimientoInventarioProductos movimiento) {
        movimiento.setTipoMovimiento("ingreso"); // Establecer tipo de movimiento como ingreso
        movimientoService.registrarIngreso(movimiento);
        return "redirect:/invProducto/listar";
    }

    @GetMapping("/salida-form")
    public String mostrarFormularioSalida(Model model) {
        List<Productos> productos = productoRepository.findAll();
        List<Empleado> empleados = empleadoRepository.findAll();
        model.addAttribute("movimiento", new MovimientoInventarioProductos());
        model.addAttribute("productos", productos);
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Registrar Salida de Producto");
        return "InvProducto/salidaForm";
    }

    @PostMapping("/salidaForm")
    public String registrarSalida(@ModelAttribute("movimiento") MovimientoInventarioProductos movimiento) {
        movimiento.setTipoMovimiento("salida"); // Establecer tipo de movimiento como salida
        movimientoService.registrarSalida(movimiento);
        return "redirect:/invProducto/listar";
    }

    @GetMapping("/eliminar/movimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id) {
        movimientoService.eliminarMovimiento(id);
        return "redirect:/invProducto/listar";
    }
}