package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Cliente;
import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.service.ClienteService;
import com.eon.hierbasanta.service.TipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TipoClienteService tipoClienteService;

    @GetMapping("/listarCliente")
    public String listarCliente(Model model) {
        List<Cliente> clientes = clienteService.mostrarTodos();
        model.addAttribute("listaClientes", clientes);
        return "Cliente/listarCliente";
    }

    @GetMapping("/insertarCliente")
    public String insertarClienteForm(Model model) {
        List<TipoCliente> tipoClientes = tipoClienteService.mostrarTodos();
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("tipoClientes", tipoClientes);
        return "Cliente/insertarCliente";
    }

    @PostMapping("/insertarCliente")
    public String insertarCliente(@ModelAttribute Cliente cliente) {
        clienteService.crearCliente(cliente);
        return "redirect:/cliente/listarCliente";
    }

    @GetMapping("/editarCliente/{id}")
    public String editarClienteForm(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        List<TipoCliente> tipoClientes = tipoClienteService.mostrarTodos();
        model.addAttribute("cliente", cliente);
        model.addAttribute("tipoClientes", tipoClientes);
        return "Cliente/editarCliente";
    }

    @PostMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        clienteService.actualizarCliente(id, cliente);
        return "redirect:/cliente/listarCliente";
    }

    @GetMapping("/detalleCliente/{id}")
    public String detalleCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        model.addAttribute("cliente", cliente);
        return "Cliente/detalleCliente";
    }

    @GetMapping("/eliminarCliente/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/cliente/listarCliente";
    }
}