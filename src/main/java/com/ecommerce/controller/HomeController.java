
package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    
   @Autowired
    private ProductoService productoService;
    
    @GetMapping("")
    public String home(Model modelo) {
        modelo.addAttribute("productos", productoService.listar());
        return "usuario/home";
    }
    
    @GetMapping("productohome/{id}")
    public String productoHome(Producto producto, Model modelo) {
        log.info("Id producto enviado como parámetro {}", producto.getId());
        var p = productoService.buscar(producto.getId()).get();
        modelo.addAttribute("producto", p);
        return "usuario/productohome";
    }
}
