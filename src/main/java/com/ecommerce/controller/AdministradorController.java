package com.ecommerce.controller;

import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.service.OrdenService;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private OrdenService ordenService;

    @GetMapping("")
    public String inicio(Model model) {
        
        List<Producto> productos = productoService.listar();
        model.addAttribute("productos", productos);
        return "administrador/index";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model modelo) {
        var usuarios = usuarioService.findAll();
        modelo.addAttribute("usuarios", usuarios);
        return "administrador/usuarios";
    }
    
    @GetMapping("/ordenes")
    public String listaCompras(Model modelo) {
        modelo.addAttribute("ordenes", ordenService.findAll());
        
        return "administrador/ordenes";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, Model modelo) {
        
        Optional<Orden> o = ordenService.buscar(id);
        modelo.addAttribute("detalles", o.get().getDetalle());
     
        return "administrador/detalleorden";
    }
}
