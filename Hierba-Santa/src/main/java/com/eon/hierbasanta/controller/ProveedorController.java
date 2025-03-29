package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Proveedor;
import com.eon.hierbasanta.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping("/listar")
    public String listarProveedores(Model model) {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        model.addAttribute("proveedores", proveedores);
        return "Proveedor/listarProveedor";
    }

    @GetMapping("/nuevo")
    public String agregarProveedor(Model model) {
        Proveedor proveedor = new Proveedor();
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("titulo", "Nuevo Proveedor");
        return "Proveedor/proveedor_form";
    }

    @PostMapping("/save")
    public String guardarProveedor(Proveedor proveedor, RedirectAttributes redirectAttributes) {
        try {
            proveedorRepository.save(proveedor);
            redirectAttributes.addFlashAttribute("mensaje", "Proveedor guardado correctamente");
            return "redirect:/proveedor/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el proveedor");
        }
        return "redirect:/proveedor/listar";
    }

    @GetMapping("/{id}")
    public String editarProveedor(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Proveedor proveedor = proveedorRepository.findById(id).orElseThrow(() -> new Exception("Proveedor no encontrado"));
            model.addAttribute("pagetitle", "Editar proveedor: " + proveedor.getIdProveedor());
            model.addAttribute("proveedor", proveedor);
            return "Proveedor/proveedor_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/proveedor/listar";
        }
    }

    @GetMapping("/delete/{id}")
    public String eliminarProveedor(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            proveedorRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Proveedor eliminado con éxito");
            return "redirect:/proveedor/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/proveedor/listar";
    }

    @GetMapping("/detalle/{id}")
    public String verDetallesProveedor(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Proveedor proveedor = proveedorRepository.findById(id).orElseThrow(() -> new Exception("Proveedor no encontrado"));
            model.addAttribute("proveedor", proveedor);
            return "Proveedor/detalleProveedor";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/proveedor/listar";
        }
    }
}