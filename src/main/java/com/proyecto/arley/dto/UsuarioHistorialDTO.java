package com.proyecto.arley.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioHistorialDTO {
    private Long idUsuario;
    private String nombreCompleto;
    private String telefono;
    private String direccion;
    private List<VehiculoDTO> vehiculos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VehiculoDTO {
        private Long idVehiculo;
        private String modelo;
    }
}
