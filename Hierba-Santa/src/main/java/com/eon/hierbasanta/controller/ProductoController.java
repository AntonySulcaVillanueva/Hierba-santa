package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.model.Categorias;
import com.eon.hierbasanta.repository.CategoriasRepositoy;
import com.eon.hierbasanta.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/producto")  // Usamos esta ruta base para todas las rutas relacionadas con productos
public class ProductoController {

    @Autowired
    private ProductosRepository productoRepository;

    @Autowired
    private CategoriasRepositoy categoriasRepositoy;

    // Listar todos los productos
    @GetMapping("/listar")
    public String listarProducto(Model model) {
        List<Productos> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "Producto/listarProducto";  // La vista está en /src/main/resources/templates/Producto/listarProducto.html
    }

    // Formulario para agregar nuevo producto
    @GetMapping("/nuevo")
    public String agregarProducto(Model model) {
        List<Categorias> categorias = categoriasRepositoy.findAll();
        Productos producto = new Productos();
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);  // Para seleccionar una categoría para el producto
        model.addAttribute("titulo", "Nuevo Producto");
        return "Producto/producto_form";  // La vista está en /src/main/resources/templates/Producto/producto_form.html
    }

    // Guardar nuevo producto
    @PostMapping("/save")
    public String guardarProducto(Productos producto, RedirectAttributes redirectAttributes) {
        try {
            productoRepository.save(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente");
            return "redirect:/producto/listar";  // Redirige a la lista de productos
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el producto");
        }
        return "redirect:/producto/listar";  // Redirige si hay un error
    }

    // Editar producto
    @GetMapping("/{id}")
    public String editarProducto(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Productos producto = productoRepository.findById(id).orElseThrow(() -> new Exception("Producto no encontrado"));
            List<Categorias> categorias = categoriasRepositoy.findAll();
            model.addAttribute("producto", producto);
            model.addAttribute("categorias", categorias);  // Para seleccionar la categoría del producto
            model.addAttribute("pagetitle", "Editar producto: " + producto.getIdproducto());
            return "Producto/producto_form";  // La vista está en /src/main/resources/templates/Producto/producto_form.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/producto/listar";  // Redirige a la lista de productos si hay error
        }
    }

    // Eliminar producto
    @GetMapping("/delete/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado con éxito");
            return "redirect:/producto/listar";  // Redirige a la lista de productos
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/producto/listar";  // Redirige si hay un error
    }

    // Ver detalles de un producto
    @GetMapping("/detalle/{id}")
    public String verDetallesProducto(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Productos producto = productoRepository.findById(id).orElseThrow(() -> new Exception("Producto no encontrado"));
            model.addAttribute("producto", producto);
            return "Producto/detalleProducto";  // La vista está en /src/main/resources/templates/Producto/detalleProducto.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/producto/listar";  // Redirige a la lista si hay error
        }
    }
}
