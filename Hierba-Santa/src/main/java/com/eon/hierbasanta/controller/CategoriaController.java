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
        return "Categoria/listarCategoria";
    }

    @GetMapping("/insertarCategoria")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categorias());
        model.addAttribute("accion", "/categoria/insertarCategoria");
        return "Categoria/insertarCategoria";
    }

    @PostMapping("/insertarCategoria")
    public String guardarCategoria(@ModelAttribute Categorias categoria) {
        categoriaService.crearCategoria(categoria);
        return "redirect:/categoria/listarCategoria";
    }

    @GetMapping("/editarCategoria/{idcategoria}")
    public String mostrarFormularioEditarCategoria(@PathVariable Long idcategoria, Model model) {
        Optional<Categorias> categoriasOptional = Optional.ofNullable(categoriaService.optenerPorId(idcategoria));
        if (categoriasOptional.isPresent()) {
            model.addAttribute("categoria", categoriasOptional.get());
            model.addAttribute("accion", "/categoria/editarCategoria/" + idcategoria);
        } else {
            return "redirect:/categoria/listarCategoria";
        }
        return "Categoria/editarCategoria";
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

    @GetMapping("/detalleCategoria/{idcategoria}")
    public String mostrarDetalleCategoria(@PathVariable Long idcategoria, Model model) {
        Optional<Categorias> categoriasOptional = Optional.ofNullable(categoriaService.optenerPorId(idcategoria));
        if (categoriasOptional.isPresent()) {
            model.addAttribute("categoria", categoriasOptional.get());
        } else {
            return "redirect:/categoria/listarCategoria";
        }
        return "Categoria/detalleCategoria";
    }
}