package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.service.TipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tipoCliente")
public class TipoClienteController {

    @Autowired
    private TipoClienteService tipoClienteService;

    @GetMapping("/listarTipoCliente")
    public String listarTipoCliente(Model model) {
        List<TipoCliente> tipoCliente = tipoClienteService.mostrarTodos();
        model.addAttribute("listaTipoCliente", tipoCliente);
        return "TipoCliente/listarTipoCliente";
    }

    @GetMapping("/insertarTipoCliente")
    public String insertarTipoClienteForm(Model model) {
        model.addAttribute("tcliente", new TipoCliente());
        return "TipoCliente/insertarTipoCliente";
    }

    @PostMapping("/insertarTipoCliente")
    public String insertarTipoCliente(@ModelAttribute TipoCliente tipoCliente) {
        tipoClienteService.crearTipoCliente(tipoCliente);
        return "redirect:/tipoCliente/listarTipoCliente";
    }

    @GetMapping("/editarTipoCliente/{id}")
    public String editarTipoClienteForm(@PathVariable Long id, Model model) {
        TipoCliente tipoCliente = tipoClienteService.optenerPorId(id);
        model.addAttribute("tipoCliente", tipoCliente);
        return "TipoCliente/editarTipoCliente";
    }

    @PostMapping("/editarTipoCliente/{id}")
    public String editarTipoCliente(@PathVariable Long id, @ModelAttribute TipoCliente tipoCliente) {
        tipoClienteService.actualizarTipoCliente(id, tipoCliente);
        return "redirect:/tipoCliente/listarTipoCliente";
    }

    @GetMapping("/detalleTipoCategoria/{id}")
    public String detalleTipoCategoria(@PathVariable Long id, Model model) {
        TipoCliente tipoCliente = tipoClienteService.optenerPorId(id);
        model.addAttribute("tipoCliente", tipoCliente);
        return "TipoCliente/detalleTipoCategoria";
    }

    @GetMapping("/eliminarTipoCliente/{id}")
    public String eliminarTipoCliente(@PathVariable Long id) {
        tipoClienteService.eliminarTipoCliente(id);
        return "redirect:/tipoCliente/listarTipoCliente";
    }
}