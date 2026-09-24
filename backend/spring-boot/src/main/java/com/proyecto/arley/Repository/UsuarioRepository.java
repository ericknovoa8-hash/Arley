package com.proyecto.arley.Repository;

import com.proyecto.arley.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método para buscar usuarios por nombre (útil para el historial)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}