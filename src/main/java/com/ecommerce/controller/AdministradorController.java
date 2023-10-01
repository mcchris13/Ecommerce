package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/administrador")
    public String inicio(Model model) {
        
        List<Producto> productos = productoService.listar();
        model.addAttribute("productos", productos);
        return "administrador/index";
    }

}
