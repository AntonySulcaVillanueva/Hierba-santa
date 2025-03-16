package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Categorias;
import com.eon.hierbasanta.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listarCategoria")
    public String listarCategoria(Model model) {
        List<Categorias> categorias = categoriaService.mostrarTodas();
        model.addAttribute("listaCategoria", categorias);
        model.addAttribute("categoria", new Categorias());
        return "Categoria/listarCategoria";
    }

    @PostMapping("/insertarCategoria")
    public String guardarCategoria(@ModelAttribute Categorias categoria) {
        categoriaService.crearCategoria(categoria);
        return "redirect:/categoria/listarCategoria";
    }

    @PostMapping("/editarCategoria/{idcategoria}")
    public String actualizarCategoria(@PathVariable Long idcategoria, @ModelAttribute Categorias categoria) {
        categoriaService.actualizarCategoria(idcategoria, categoria);
        return "redirect:/categoria/listarCategoria";
    }

    @GetMapping("/eliminar/{idcategoria}")
    public String eliminarCategoria(@PathVariable Long idcategoria) {
        categoriaService.eliminarCategoria(idcategoria);
        return "redirect:/categoria/listarCategoria";
    }

    @GetMapping("/obtenerCategoria/{idcategoria}")
    @ResponseBody
    public Categorias obtenerCategoria(@PathVariable Long idcategoria) {
        return categoriaService.optenerPorId(idcategoria);
    }
}