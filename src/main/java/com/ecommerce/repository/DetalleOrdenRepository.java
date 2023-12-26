
package com.ecommerce.repository;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>{
    List<DetalleOrden> findByOrden(Orden orden);
}
