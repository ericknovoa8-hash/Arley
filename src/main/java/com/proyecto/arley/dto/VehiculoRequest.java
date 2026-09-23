package com.proyecto.arley.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehiculoRequest {
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
}
