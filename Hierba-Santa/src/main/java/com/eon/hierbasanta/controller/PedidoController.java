package com.eon.hierbasanta.controller;

import com.eon.hierbasanta.Util.Constants;
import com.eon.hierbasanta.model.Cliente;
import com.eon.hierbasanta.model.Pedido;
import com.eon.hierbasanta.model.ProductoPedido;
import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.model.TipoCliente;
import com.eon.hierbasanta.repository.ClienteRepository;
import com.eon.hierbasanta.repository.PedidoRepository;
import com.eon.hierbasanta.repository.ProductosRepository;
import com.eon.hierbasanta.repository.TipoClienteRepository;
import com.eon.hierbasanta.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductosRepository productoRepository;
    private final TipoClienteRepository tipoClienteRepository;
    private final PedidoService pedidoService;

    private static final AtomicInteger currentMaxId = new AtomicInteger(Constants.MAX_PRODUCTO_ID);

    @Autowired
    public PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                            ProductosRepository productoRepository, TipoClienteRepository tipoClienteRepository,
                            PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.tipoClienteRepository = tipoClienteRepository;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listar")
    public String listarPedidos(Model model) {
        List<Productos> productos = productoRepository.findAll();
        List<Pedido> pedidos = pedidoService.findAll();
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("productos", productos);
        return "Pedido/listarPedidos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setFechaPedido(new Date()); // Establecer la fecha actual

        List<Cliente> clientes = clienteRepository.findAll();
        List<Productos> productos = productoRepository.findAll();
        List<TipoCliente> tiposDeCliente = tipoClienteRepository.findAll();
        List<String> departamentos = Arrays.asList("Amazonas", "Áncash", "Apurímac", "Arequipa", "Ayacucho", "Cajamarca", "Callao", "Cusco", "Huancavelica", "Huánuco", "Ica", "Junín", "La Libertad", "Lambayeque", "Lima", "Loreto", "Madre de Dios", "Moquegua", "Pasco", "Piura", "Puno", "San Martín", "Tacna", "Tumbes", "Ucayali");
        List<String> distritos = Arrays.asList("Distrito1", "Distrito2", "Distrito3"); // Lista de distritos según el departamento

        model.addAttribute("pedido", nuevoPedido);
        model.addAttribute("clientes", clientes);
        model.addAttribute("productos", productos);
        model.addAttribute("tiposDeCliente", tiposDeCliente);
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("distritos", distritos);
        model.addAttribute("ruc", Constants.RUC);
        model.addAttribute("empresa", Constants.EMPRESA);
        model.addAttribute("direccion", Constants.DIRECCION);
        model.addAttribute("telefono", Constants.TELEFONO);
        model.addAttribute("telefonoFijo", Constants.TELEFONO_FIJO);
        model.addAttribute("distrito", Constants.DISTRITO);
        return "Pedido/crearPedido";
    }

    @PostMapping("/save")
    public String guardarPedido(@ModelAttribute Pedido pedido, RedirectAttributes redirectAttributes, @RequestParam String departamento, @RequestParam String distrito, @RequestParam String direccion) {
        try {
            // Establecer la fecha actual antes de guardar
            pedido.setFechaPedido(new Date());
            pedido.setEstadoPedido(pedido.getEstadoPedido() != null ? pedido.getEstadoPedido() : "sin cancelar");

            // Crear la dirección combinada
            String direccionEnvio = departamento + " - " + distrito + " - " + direccion;
            pedido.setDireccionEnvio(direccionEnvio);

            // Crear un ID único para cada ProductoPedido y actualizar el ID más alto
            for (ProductoPedido producto : pedido.getProductos()) {
                int newId = currentMaxId.incrementAndGet();
                producto.setIdProductoPedido(newId);
                Constants.MAX_PRODUCTO_ID = newId;
            }

            // Almacenar los IDs de ProductoPedido en la columna productosIds
            pedido.setProductosIds(pedido.getProductos().stream()
                    .map(ProductoPedido::getIdProductoPedido)
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));

            pedidoRepository.save(pedido);
            redirectAttributes.addFlashAttribute("mensaje", "Pedido guardado correctamente");
            return "redirect:/pedido/detalle/" + pedido.getIdPedido();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el pedido");
            return "redirect:/pedido/nuevo";
        }
    }

    @GetMapping("/detalle/{id}")
    public String verDetallePedido(@PathVariable Integer id, Model model) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            model.addAttribute("pedido", pedido);
            return "Pedido/detallePedido";
        } else {
            return "redirect:/pedido/listar";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            pedidoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Pedido eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el pedido");
        }
        return "redirect:/pedido/listar";
    }
}