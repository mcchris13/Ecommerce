
package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdministradorController {
   
    @GetMapping("/")
    public String inicio(){
        return "index";
    }
}
