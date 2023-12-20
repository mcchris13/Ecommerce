
package com.ecommerce.service;

import com.ecommerce.model.Orden;
import java.util.List;


public interface OrdenService {
    public Orden save(Orden orden);
    List<Orden> findAll();
    String generarNumeroOrden();
}
