package com.proyecto.arley.Service;

import com.proyecto.arley.Entity.Usuario;
import com.proyecto.arley.Entity.Vehiculo;
import com.proyecto.arley.Repository.UsuarioRepository;
import com.proyecto.arley.dto.UsuarioHistorialDTO;
import com.proyecto.arley.dto.UsuarioRegistroRequest;
import com.proyecto.arley.dto.UsuarioResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO registrarUsuario(UsuarioRegistroRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setModelo(request.getModeloVehiculo());
        vehiculo.setUsuario(usuario);

        usuario.setVehiculos(Collections.singletonList(vehiculo));

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return mapearAResponseDTO(usuarioGuardado);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioHistorialDTO> obtenerHistorialPorNombre(String nombre) {
        List<Usuario> usuarios = (nombre != null && !nombre.isEmpty()) 
            ? usuarioRepository.findByNombreContainingIgnoreCase(nombre) 
            : usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> {
            UsuarioHistorialDTO dto = new UsuarioHistorialDTO();
            dto.setIdUsuario(usuario.getId());
            dto.setNombreCompleto(usuario.getNombre());
            
            List<UsuarioHistorialDTO.VehiculoDTO> vehiculosDTO = usuario.getVehiculos() != null ? usuario.getVehiculos().stream().map(v -> {
                UsuarioHistorialDTO.VehiculoDTO vDto = new UsuarioHistorialDTO.VehiculoDTO();
                vDto.setIdVehiculo(v.getId());
                vDto.setModelo(v.getModelo());
                return vDto;
            }).collect(Collectors.toList()) : Collections.emptyList();

            dto.setVehiculos(vehiculosDTO);
            return dto;
        }).collect(Collectors.toList());
    }

    // ➕ MÉTODO FALTANTE: Actualizar
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRegistroRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapearAResponseDTO(usuarioActualizado);
    }

    // ➕ MÉTODO FALTANTE: Eliminar
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO mapearAResponseDTO(Usuario usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        response.setVehiculos(
            usuario.getVehiculos() != null ? usuario.getVehiculos().stream()
                .map(Vehiculo::getModelo)
                .collect(Collectors.toList()) : Collections.emptyList()
        );
        return response;
    }
}