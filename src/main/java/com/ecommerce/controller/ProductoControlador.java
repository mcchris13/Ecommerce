package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductoControlador {
    //Clase para saber si se están capturando los valores del objeto Producto
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoControlador.class);   
    
    @Autowired
    private ProductoService productoService;
            
    @GetMapping("/productos")
    public String show(Model modelo) {
//        Usuario u = new Usuario(1,"","","","","","","");
//        
//        var producto = new Producto(1,"","","",3,3);
//        var producto2 = new Producto(2,"","asd","",33,3);
//        producto.setUsuario(u);
//        producto2.setUsuario(u);
//        List<Producto> productos = new ArrayList<Producto>();
//        productos.add(producto);
//        productos.add(producto2);
        //modelo.addAttribute("mensajes", mensajes);
        var productos = productoService.listar();
        modelo.addAttribute("productos", productos);
        return "productos/show";
    }

    @GetMapping("/productos/agregar")
    public String create() {
        return "productos/create";
    }
    
    @PostMapping("/productos/guardar")
    public String guardar(Producto producto) {
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario(1,"","","","","","","");
        producto.setUsuario(u);
        productoService.guardar(producto);
        return "redirect:/productos";
    }
    
    @GetMapping("productos/editar/{id}")
    public String editar(Producto producto, Model modelo) {
        var productoOptional = productoService.buscar(producto.getId());
        var produc = productoOptional.get();
        LOGGER.info("Producto buscado {}", produc);
        modelo.addAttribute("producto", produc);
        return "productos/edit";
    }
    
    @GetMapping("productos/eliminar/{id}")
    public String eliminar(Producto producto) {
        productoService.eliminar(producto.getId());
        return "redirect:/productos";
    }
}
