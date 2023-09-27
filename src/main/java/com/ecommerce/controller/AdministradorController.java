
package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/administrador")
public class AdministradorController {
   
    @GetMapping("/administrador")
    public String inicio(){
        return "administrador/index";
    }
}
