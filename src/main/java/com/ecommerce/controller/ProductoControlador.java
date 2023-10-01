package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.SubirArchivoService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductoControlador {

    //Clase para saber si se están capturando los valores del objeto Producto
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private SubirArchivoService subirArchivoService;

    @GetMapping("/productos")
    public String show(Model modelo) {
        var productos = productoService.listar();
        modelo.addAttribute("productos", productos);
        return "productos/show";
    }

    @GetMapping("/productos/agregar")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/productos/guardar")
    public String guardar(Producto producto, @RequestParam("img") MultipartFile file) {
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        //imagen
        if (producto.getId() == null) { //cuando se crea un producto
            String nombreImagen = subirArchivoService.guadarImagen(file);
            producto.setImagen(nombreImagen);
        }
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String editar(Producto producto, Model modelo) {
        var productoOptional = productoService.buscar(producto.getId());
        var produc = productoOptional.get();
        LOGGER.info("Producto buscado {}", produc);
        modelo.addAttribute("producto", produc);
        return "productos/edit";
    }

    @PostMapping("/productos/actualizar")
    public String actualizar(Producto producto, @RequestParam("img") MultipartFile file) {
        Producto p = new Producto();
        p = productoService.buscar(producto.getId()).get();
        if (file.isEmpty()) {   //Cuando editamos y no cargamos un nueva imagen
            
            producto.setImagen(p.getImagen());
        } else {    //Cuando se edita la imagen
            if (!p.getImagen().equals("default.jpg")) {
                subirArchivoService.eliminarImagen(producto.getImagen());
            }
            String nombreImagen = subirArchivoService.guadarImagen(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.actualizar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(Producto producto) {
        if (!producto.getId().equals("default.jpg")) {
            subirArchivoService.eliminarImagen(producto.getImagen());
        }
        productoService.eliminar(producto.getId());
        return "redirect:/productos";
    }
}
