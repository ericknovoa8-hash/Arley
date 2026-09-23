package com.proyecto.arley.dto;

import java.util.List;
import lombok.Data;

@Data 
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String correo;
    private List<String> vehiculos;
}
