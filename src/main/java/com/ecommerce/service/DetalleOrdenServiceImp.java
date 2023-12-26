
package com.ecommerce.service;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import com.ecommerce.repository.DetalleOrdenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImp implements DetalleOrdenService{

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    

    
    
}
