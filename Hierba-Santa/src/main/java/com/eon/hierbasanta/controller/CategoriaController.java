package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Categorias;
import com.eon.hierbasanta.repository.CategoriasRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categoria")  // Usamos esta ruta base para todas las rutas relacionadas con categorías
public class CategoriaController {

    @Autowired
    private CategoriasRepositoy categoriasRepositoy;


    // Listar todas las categorías
    @GetMapping("/listar")
    public String listarCategoria(Model model) {
        List<Categorias> categorias = categoriasRepositoy.findAll();
        model.addAttribute("categorias", categorias);
        return "Categoria/listarCategoria";  // La vista está en /src/main/resources/templates/Categoria/listarCategoria.html
    }

    // Formulario para agregar nueva categoría
    @GetMapping("/nuevo")
    public String agregarCategoria(Model model) {
        Categorias categoria = new Categorias();
        model.addAttribute("categoria", categoria);
        model.addAttribute("titulo", "Nueva Categoria");
        return "Categoria/categoria_form";  // La vista está en /src/main/resources/templates/Categoria/categoria_form.html
    }

    // Guardar nueva categoría
    @PostMapping("/save")
    public String guardarCategoria(Categorias categoria, RedirectAttributes redirectAttributes) {
        try {
            categoriasRepositoy.save(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría guardada correctamente");
            return "redirect:/categoria/listar";  // Redirige a la lista de categorías
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar la categoría");
        }
        return "redirect:/categoria/listar";  // Redirige si hay un error
    }

    // Editar categoría
    @GetMapping("/{id}")
    public String editarCategoria(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Categorias categoria = categoriasRepositoy.findById(id).orElseThrow(() -> new Exception("Categoría no encontrada"));
            model.addAttribute("pagetitle", "Editar categoría: " + categoria.getIdcategoria());
            model.addAttribute("categoria", categoria);
            return "Categoria/categoria_form";  // La vista está en /src/main/resources/templates/Categoria/categoria_form.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/categoria/listar";  // Redirige a la lista de categorías si hay error
        }
    }

    // Eliminar categoría
    @GetMapping("/delete/{id}")
    public String eliminarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoriasRepositoy.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada con éxito");
            return "redirect:/categoria/listar";  // Redirige a la lista de categorías
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/categoria/listar";  // Redirige si hay un error
    }
    // Ver detalles de una categoría
    @GetMapping("/detalle/{id}")
    public String verDetallesCategoria(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Categorias categoria = categoriasRepositoy.findById(id).orElseThrow(() -> new Exception("Categoría no encontrada"));
            model.addAttribute("categoria", categoria);
            return "Categoria/detalleCategoria";  // La vista está en /src/main/resources/templates/Categoria/detalleCategoria.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/categoria/listar";  // Redirige a la lista si hay error
        }
    }

}
