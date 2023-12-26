package com.ecommerce.controller;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.DetalleOrdenService;
import com.ecommerce.service.OrdenService;
import com.ecommerce.service.ProductoService;
import com.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private OrdenService ordenService;
    
    @Autowired
    private DetalleOrdenService detalleOrdenService;
    
    //Detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden> ();
    //Datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model modelo, HttpSession session) {
        log.info("Sesion del usuario: {}", session.getAttribute("idUsuario"));
        modelo.addAttribute("productos", productoService.listar());
        
        modelo.addAttribute("session", session.getAttribute("idUsuario"));
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
    public String getCart(Model modelo, HttpSession session) {
        
        modelo.addAttribute("carrito", detalles);
        modelo.addAttribute("orden", orden);
        modelo.addAttribute("session", session.getAttribute("idUsuario"));
        return "/usuario/carrito";
    }
    
    @GetMapping("/orden")
    public String order(Model model, HttpSession session) {
        //log.info("Usuario: {}", usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString() )).get());
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString() )).get();
        
        model.addAttribute("carrito", detalles);
        model.addAttribute("orden", orden);
        
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorden";
    }
    
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());
        
        //Usuario
        Usuario u = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString() )).get();
        
        orden.setUsuario(u);
        ordenService.save(orden);
        
        //guardar detalles
        for (DetalleOrden dt : detalles) {
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
        
        //limpiar lista y orden
        orden = new Orden();
        detalles.clear();
        return "redirect:/";
    }
    
    @PostMapping("/buscar")      //@RequestParam es para recibir un dato de la vista
    public String buscarProducto(@RequestParam String nombre, Model modelo) { 
        log.info("Nombre del producto: {}", nombre);
        List<Producto> productos =  productoService.listar().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        modelo.addAttribute("productos", productos);
        return "usuario/home";
    }
}
