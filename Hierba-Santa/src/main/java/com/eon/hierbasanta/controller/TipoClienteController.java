package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.repository.TipoClienteRepository;
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
@RequestMapping("/tipoCliente")
public class TipoClienteController {

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @GetMapping("/listar")
    public String listarTipoClientes(Model model) {
        List<TipoCliente> tipoClientes = tipoClienteRepository.findAll();
        model.addAttribute("tipoClientes", tipoClientes);
        return "TipoCliente/listarTipoCliente";
    }

    @GetMapping("/nuevo")
    public String agregarTipoCliente(Model model) {
        TipoCliente tipoCliente = new TipoCliente();
        model.addAttribute("tipoCliente", tipoCliente);
        model.addAttribute("titulo", "Nuevo Tipo de Cliente");
        return "TipoCliente/tipoCliente_form";
    }

    @PostMapping("/save")
    public String guardarTipoCliente(TipoCliente tipoCliente, RedirectAttributes redirectAttributes) {
        try {
            tipoClienteRepository.save(tipoCliente);
            redirectAttributes.addFlashAttribute("mensaje", "Tipo de Cliente guardado correctamente");
            return "redirect:/tipoCliente/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el Tipo de Cliente");
        }
        return "redirect:/tipoCliente/listar";
    }

    @GetMapping("/{id}")
    public String editarTipoCliente(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            TipoCliente tipoCliente = tipoClienteRepository.findById(id).orElseThrow(() -> new Exception("Tipo de Cliente no encontrado"));
            model.addAttribute("pagetitle", "Editar Tipo de Cliente: " + tipoCliente.getIdTipoCliente());
            model.addAttribute("tipoCliente", tipoCliente);
            return "TipoCliente/tipoCliente_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/tipoCliente/listar";
        }
    }

    @GetMapping("/delete/{id}")
    public String eliminarTipoCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            tipoClienteRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Tipo de Cliente eliminado con éxito");
            return "redirect:/tipoCliente/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/tipoCliente/listar";
    }

    @GetMapping("/detalle/{id}")
    public String verDetallesTipoCliente(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            TipoCliente tipoCliente = tipoClienteRepository.findById(id).orElseThrow(() -> new Exception("Tipo de Cliente no encontrado"));
            model.addAttribute("tipoCliente", tipoCliente);
            return "TipoCliente/detalleTipoCliente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/tipoCliente/listar";
        }
    }
}