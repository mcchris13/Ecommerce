
package com.ecommerce.service;

import com.ecommerce.model.Producto;
import java.util.Optional;


public interface ProductoService {
    public Producto guardar(Producto producto);
    public Optional<Producto> buscar(Integer id);
    public void actualizar(Producto producto);
    public void eliminar(Integer id);
}
