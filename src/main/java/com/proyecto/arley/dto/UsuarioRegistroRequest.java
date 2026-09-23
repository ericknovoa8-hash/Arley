package com.proyecto.arley.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class UsuarioRegistroRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @JsonAlias("email")
    private String correo;

    @NotBlank(message = "El modelo del vehículo es obligatorio")
    private String modeloVehiculo;
}
