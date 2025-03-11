package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Categorias;
import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.service.CategoriaService;
import com.eon.hierbasanta.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listarProductos")
    public String listarProductos(Model model) {
        List<Productos> productos = productoService.mostrarTodas();
        model.addAttribute("listaProductos", productos);
        return "Producto/listarProductos";
    }

    @GetMapping("/insertarProducto")
    public String mostrarFormularioNuevoProducto(Model model) {
        List<Categorias> categorias = categoriaService.mostrarTodas();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", new Productos());
        model.addAttribute("accion", "/producto/insertarProducto");
        return "Producto/insertarProducto";
    }

    @PostMapping("/insertarProducto")
    public String guardarProducto(@ModelAttribute Productos producto) {
        productoService.crearProducto(producto);
        return "redirect:/producto/listarProductos";
    }

    @GetMapping("/editarProducto/{idproducto}")
    public String mostrarFormularioEditarProducto(@PathVariable Long idproducto, Model model) {
        Optional<Productos> productosOptional = Optional.ofNullable(productoService.optenerPorId(idproducto));
        if (productosOptional.isPresent()) {
            List<Categorias> categorias = categoriaService.mostrarTodas();
            model.addAttribute("producto", productosOptional.get());
            model.addAttribute("categorias", categorias);
            model.addAttribute("accion", "/producto/editarProducto/" + idproducto);
        } else {
            return "redirect:/producto/listarProductos";
        }
        return "Producto/editarProducto";
    }

    @PostMapping("/editarProducto/{idproducto}")
    public String actualizarProducto(@PathVariable Long idproducto, @ModelAttribute Productos producto) {
        productoService.actualizarProducto(idproducto, producto);
        return "redirect:/producto/listarProductos";
    }

    @GetMapping("/eliminar/{idproducto}")
    public String eliminarProducto(@PathVariable Long idproducto) {
        productoService.eliminarProducto(idproducto);
        return "redirect:/producto/listarProductos";
    }

    @GetMapping("/detalleProducto/{idproducto}")
    public String mostrarDetalleProducto(@PathVariable Long idproducto, Model model) {
        Optional<Productos> productosOptional = Optional.ofNullable(productoService.optenerPorId(idproducto));
        if (productosOptional.isPresent()) {
            model.addAttribute("producto", productosOptional.get());
        } else {
            return "redirect:/producto/listarProductos";
        }
        return "Producto/detalleProducto";
    }
}