
package com.ecommerce.service;

import com.ecommerce.model.Usuario;
import java.util.List;
import java.util.Optional;


public interface UsuarioService {
    Optional<Usuario> findById(Integer id);
    Usuario guardar(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
}
