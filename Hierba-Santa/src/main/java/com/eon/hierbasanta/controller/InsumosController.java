package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Insumos;
import com.eon.hierbasanta.model.Categorias;
import com.eon.hierbasanta.model.Proveedor;
import com.eon.hierbasanta.repository.CategoriasRepositoy;
import com.eon.hierbasanta.repository.InsumosRepository;
import com.eon.hierbasanta.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/insumo")
public class InsumosController {

    @Autowired
    private InsumosRepository insumosRepository;

    @Autowired
    private CategoriasRepositoy categoriasRepositoy;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping("/listar")
    public String listarInsumos(Model model) {
        List<Insumos> insumos = insumosRepository.findAll();
        model.addAttribute("insumos", insumos);
        return "Insumo/listarInsumos";
    }

    @GetMapping("/nuevo")
    public String agregarInsumo(Model model) {
        List<Categorias> categorias = categoriasRepositoy.findAll();
        List<Proveedor> proveedores = proveedorRepository.findAll();
        Insumos insumo = new Insumos();
        model.addAttribute("insumo", insumo);
        model.addAttribute("categorias", categorias);
        model.addAttribute("proveedores", proveedores);
        model.addAttribute("pageTitle", "Nuevo Insumo");
        return "Insumo/insumo_form";
    }

    @PostMapping("/save")
    public String guardarInsumo(Insumos insumo, RedirectAttributes redirectAttributes) {
        try {
            insumosRepository.save(insumo);
            redirectAttributes.addFlashAttribute("mensaje", "Insumo guardado correctamente");
            return "redirect:/insumo/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el insumo");
        }
        return "redirect:/insumo/listar";
    }

    @GetMapping("/{id}")
    public String editarInsumo(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Insumos insumo = insumosRepository.findById(id).orElseThrow(() -> new Exception("Insumo no encontrado"));
            List<Categorias> categorias = categoriasRepositoy.findAll();
            List<Proveedor> proveedores = proveedorRepository.findAll();
            model.addAttribute("insumo", insumo);
            model.addAttribute("categorias", categorias);
            model.addAttribute("proveedores", proveedores);
            model.addAttribute("pageTitle", "Editar insumo: " + insumo.getNombre());
            return "Insumo/insumo_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/insumo/listar";
        }
    }

    @GetMapping("/delete/{id}")
    public String eliminarInsumo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            insumosRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Insumo eliminado con éxito");
            return "redirect:/insumo/listar";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/insumo/listar";
    }

    @GetMapping("/detalle/{id}")
    public String verDetallesInsumo(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Insumos insumo = insumosRepository.findById(id).orElseThrow(() -> new Exception("Insumo no encontrado"));
            model.addAttribute("insumo", insumo);
            model.addAttribute("pageTitle", "Detalles del Insumo: " + insumo.getNombre());
            return "Insumo/detalleInsumo";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/insumo/listar";
        }
    }
}