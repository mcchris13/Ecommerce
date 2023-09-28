
package com.ecommerce.service;

import com.ecommerce.model.Producto;
import com.ecommerce.repository.ProductoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    //Injecta en el controlar esta clase
public class ProductoServiceImp implements ProductoService{

    @Autowired  //Es para indicar que se va a injectar un objeto a esta clase
    private ProductoRepository productoRepository;
    
    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);    //Si el save tiene como Id = null crea un nuevo objeto
    }

    @Override
    public Optional<Producto> buscar(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public void actualizar(Producto producto) {
        productoRepository.save(producto);   //Si el save tiene como Id = "1" actualiza el objeto
    }

    @Override
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> listar() {
        return (List<Producto>) productoRepository.findAll();
    }
}
