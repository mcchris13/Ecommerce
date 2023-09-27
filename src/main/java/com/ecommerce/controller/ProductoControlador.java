
package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoControlador {
    
    @GetMapping("/productos")
   public String show() {
       return "productos/show";
   }
}
