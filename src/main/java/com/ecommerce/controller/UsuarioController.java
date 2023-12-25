
package com.ecommerce.controller;

import com.ecommerce.model.Usuario;
import com.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
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
    
    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }
    
    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        logger.info("Accesos: {}", usuario);
        
        //Autenticación por email
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        //logger.info("Usuario de BD: {}", user.get());
        //Validación del TIPO de usuario
        if(user.isPresent()) {
            session.setAttribute("idUsuario", user.get().getId());
            if (user.get().getTipo().equals("ADMIN")) {
                return "redirect:/administrador";
            }else {
                return "redirect:/";
            }
        }else {
            logger.info("Usuario no existe");
        }
        return "redirect:/";
    }
}