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
        List<Categorias> categorias=categoriaService.mostrarTodas();
        model.addAttribute("listaCategoria", categorias);
        return "categoria/listarCategoria";
    }
    @GetMapping("/nuevaCategoria")
    public String MostrarformularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categorias());
        model.addAttribute("accion", "");
        return "Categoria/formularioCategoria";
    }

    @PostMapping("/nuevaCategoria")
    public String GuardarCategoria(@ModelAttribute Categorias categoria) {
        categoriaService.crearCategoria(categoria);
        return "redirect:/categoria/listarCategoria";
    }

    @GetMapping("/editarCategoria/{idcategoria}")
    public String MostrarformularioEditarCategoria(@PathVariable Long idcategoria, @ModelAttribute Categorias categoria,Model model) {
        Optional<Categorias> categoriasOptional=Optional.ofNullable(categoriaService.optenerPorId(idcategoria));
        if(categoriasOptional.isPresent()) {
            model.addAttribute("categoria", categoriasOptional.get());
            model.addAttribute("accion", ""+idcategoria);
        }else{
            return "redirect:/categoria/listarCategoria";
        }
        return "Categoria/formularioCategoria";
    }

    @PostMapping("/editarCategoria/{idCategoria}")
    public String ActualizarCategoria(@PathVariable Long idCategoria,@ModelAttribute Categorias categoria) {
        categoriaService.actualizarCategoria(idCategoria, categoria);
        return "redirect:/categoria/listarCategoria";
    }


    @GetMapping("/eliminar/{idcategoria}")
    public String EliminarCategoria(@PathVariable Long idcategoria) {
        categoriaService.eliminarCategoria(idcategoria);
        return "redirect:/categoria/listarCategoria";
    }

}
