
package com.ecommerce.controller;

import com.ecommerce.model.Usuario;
import com.ecommerce.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
   
    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    
    @Autowired
    private UsuarioService usuarioService;
    
    //   /usuario/registro
    @GetMapping("/registro")
    public String crear() {
        return "usuario/registro";
    }
    
    // Se usa PostMapping cuando se envía información dentro de un formulario
    @PostMapping("/guardar")
    public String salvar(Usuario usuario) {
        logger.info("Usuario registro: {}", usuario);
        usuario.setTipo("USUARIO");
        usuarioService.guardar(usuario);
        return "redirect:/";
    }
}
