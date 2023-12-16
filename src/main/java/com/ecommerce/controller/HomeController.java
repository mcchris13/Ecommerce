package com.ecommerce.controller;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;
    
    //Detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden> ();
    //Datos de la orden
    Orden orden = new Orden();

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

    @PostMapping("carrito")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model modelo) {
        DetalleOrden detalleOrden = new DetalleOrden();
        double sumaTotal = 0;
        
        var producto = productoService.buscar(id).get();
        log.info("Producto {}", producto);
        log.info("Cantidad {}", cantidad);
        
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);
        
        
        //Validar que el producto no se añada 2 veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
        if (!ingresado) {
            detalles.add(detalleOrden);
        }
        
        
        
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        
        modelo.addAttribute("carrito", detalles);
        modelo.addAttribute("orden", orden);
        return "usuario/carrito";
    }
    
    @GetMapping("delete/carrito/{id}")
    public String eliminarProductoCarrito(@PathVariable Integer id, Model modelo) {
        //Nueva lista
        List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden> ();
        for(DetalleOrden detalleOrden : detalles){
            if(detalleOrden.getProducto().getId() != id) {
                ordenesNuevas.add(detalleOrden);
            }
        }
        //Poner la nueva lista con los productos restantes
        detalles = ordenesNuevas;
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        
        modelo.addAttribute("carrito", detalles);
        modelo.addAttribute("orden", orden);
        
        return "usuario/carrito";
    }
    
    @GetMapping("/getCart")
    public String getCart(Model model) {
        
        model.addAttribute("carrito", detalles);
        model.addAttribute("orden", orden);
        return "/usuario/carrito";
    }
}
