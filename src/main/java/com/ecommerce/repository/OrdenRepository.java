
package com.ecommerce.repository;

import com.ecommerce.model.Orden;
import com.ecommerce.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer>{
    List<Orden> findByUsuario(Usuario usuario);
}
