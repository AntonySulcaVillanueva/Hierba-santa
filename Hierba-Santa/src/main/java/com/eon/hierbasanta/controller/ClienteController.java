package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.model.Cliente;
import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.service.ClienteService;
import com.eon.hierbasanta.service.TipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        model.addAttribute("tcliente", tipoClientes);
        return "Cliente/insertarCliente";
    }

    @PostMapping("/insertarCliente")
    public String insertarCliente(@ModelAttribute Cliente cliente) {
        cliente.setFechaRegistro(LocalDateTime.now());
        clienteService.crearCliente(cliente);
        return "redirect:/cliente/listarCliente";
    }

    @GetMapping("/editarCliente/{idCliente}")
    public String editarClienteForm(@PathVariable Long idCliente, Model model) {
        Cliente cliente = clienteService.obtenerPorId(idCliente);
        List<TipoCliente> tipoClientes = tipoClienteService.mostrarTodos();
        model.addAttribute("llaveCliente", cliente);
        model.addAttribute("llavetipoClientes", tipoClientes);
        return "Cliente/editarCliente";
    }

    @PostMapping("/editarCliente/{idCliente}")
    public String editarCliente(@PathVariable Long idCliente, @ModelAttribute Cliente cliente) {
        clienteService.actualizarCliente(idCliente, cliente);
        return "redirect:/cliente/listarCliente";
    }

    @GetMapping("/detalleCliente/{idCliente}")
    public String detalleCliente(@PathVariable Long idCliente, Model model) {
        Cliente cliente = clienteService.obtenerPorId(idCliente);
        model.addAttribute("llaveCliente", cliente);
        return "Cliente/detalleCliente";
    }

    @GetMapping("/eliminarCliente/{idCliente}")
    public String eliminarCliente(@PathVariable Long idCliente) {
        clienteService.eliminarCliente(idCliente);
        return "redirect:/cliente/listarCliente";
    }
}