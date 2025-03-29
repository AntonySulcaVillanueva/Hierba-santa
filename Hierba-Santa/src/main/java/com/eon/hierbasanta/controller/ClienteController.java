package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Cliente;
import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.repository.ClienteRepository;
import com.eon.hierbasanta.repository.TipoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "Cliente/listarCliente";
    }

    @GetMapping("/nuevo")
    public String agregarCliente(Model model) {
        List<TipoCliente> tiposCliente = tipoClienteRepository.findAll();
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("tiposCliente", tiposCliente);
        model.addAttribute("titulo", "Nuevo Cliente");
        return "Cliente/cliente_form";
    }

    @PostMapping("/save")
    public String guardarCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            cliente.setFechaRegistro(LocalDateTime.now());
            if (cliente.getTotalPedidosSinCancelar() == null) {
                cliente.setTotalPedidosSinCancelar(0);
            }
            clienteRepository.save(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado correctamente");
            return "redirect:/cliente/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el Cliente");
        }
        return "redirect:/cliente/listar";
    }

    @GetMapping("/{id}")
    public String editarCliente(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new Exception("Cliente no encontrado"));
            List<TipoCliente> tiposCliente = tipoClienteRepository.findAll();
            model.addAttribute("pagetitle", "Editar Cliente: " + cliente.getIdCliente());
            model.addAttribute("cliente", cliente);
            model.addAttribute("tiposCliente", tiposCliente);
            return "Cliente/cliente_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/cliente/listar";
        }
    }

    @GetMapping("/delete/{id}")
    public String eliminarCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            clienteRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado con éxito");
            return "redirect:/cliente/listar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/cliente/listar";
    }

    @GetMapping("/detalle/{id}")
    public String verDetallesCliente(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new Exception("Cliente no encontrado"));
            model.addAttribute("cliente", cliente);
            return "Cliente/detalleCliente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/cliente/listar";
        }
    }
}