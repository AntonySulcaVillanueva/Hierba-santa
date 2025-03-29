package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Empleado;
import com.eon.hierbasanta.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Listar todos los empleados
    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoRepository.findAll();
        model.addAttribute("empleados", empleados);
        return "Empleado/listarEmpleado";  // La vista está en /src/main/resources/templates/Empleado/listarEmpleado.html
    }

    // Formulario para agregar nuevo empleado
    @GetMapping("/nuevo")
    public String agregarEmpleado(Model model) {
        Empleado empleado = new Empleado();
        model.addAttribute("empleado", empleado);
        model.addAttribute("titulo", "Nuevo Empleado");
        return "Empleado/empleado_form";  // La vista está en /src/main/resources/templates/Empleado/empleado_form.html
    }

    // Guardar nuevo empleado
    @PostMapping("/save")
    public String guardarEmpleado(Empleado empleado, @RequestParam(value = "fechaDeContrato", required = false) String fechaDeContratoString, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Fecha de Contrato recibida: " + fechaDeContratoString);

            // Convertir String a LocalDate si no es nulo
            if (fechaDeContratoString != null && !fechaDeContratoString.isEmpty()) {
                // Eliminar caracteres adicionales al final de la cadena
                fechaDeContratoString = fechaDeContratoString.replaceAll("[^\\d\\-]", "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                empleado.setFechaDeContrato(LocalDate.parse(fechaDeContratoString, formatter));
            }

            empleadoRepository.save(empleado);
            redirectAttributes.addFlashAttribute("mensaje", "Datos del empleado guardados correctamente.");
            return "redirect:/empleado/listar";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error: DNI duplicado o datos inválidos.");
            return "redirect:/empleado/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el empleado: " + e.getMessage());
            return "redirect:/empleado/listar";
        }
    }

    // Editar empleado
    @GetMapping("/{id}")
    public String editarEmpleado(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new Exception("Empleado no encontrado"));

            // Formatear LocalDate a String (YYYY-MM-dd)
            if (empleado.getFechaDeContrato() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fechaFormateada = empleado.getFechaDeContrato().format(formatter);
                model.addAttribute("fechaFormateada", fechaFormateada);
            }

            model.addAttribute("titulo", "Editar empleado: " + empleado.getNombreCompleto());
            model.addAttribute("empleado", empleado);
            return "Empleado/empleado_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/empleado/listar";
        }
    }

    // Eliminar empleado
    @GetMapping("/delete/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            empleadoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado eliminado con éxito");
            return "redirect:/empleado/listar";
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error: Empleado no encontrado.");
            return "redirect:/empleado/listar";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el empleado: " + exception.getMessage());
            return "redirect:/empleado/listar";
        }
    }

    // Ver detalles de un empleado
    @GetMapping("/detalle/{id}")
    public String verDetallesEmpleado(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new Exception("Empleado no encontrado"));
            model.addAttribute("empleado", empleado);
            return "Empleado/detalleEmpleado";  // La vista está en /src/main/resources/templates/Empleado/detalleEmpleado.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/empleado/listar";  // Redirige a la lista si hay error
        }
    }
}