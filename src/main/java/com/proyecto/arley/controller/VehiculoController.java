package com.proyecto.arley.controller;

import com.proyecto.arley.Entity.Vehiculo;
import com.proyecto.arley.Entity.Usuario;
import com.proyecto.arley.Repository.VehiculoRepository;
import com.proyecto.arley.Repository.UsuarioRepository;
import com.proyecto.arley.dto.VehiculoRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public VehiculoController(
        VehiculoRepository vehiculoRepository,
        UsuarioRepository usuarioRepository
    ) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculo(@PathVariable Long id) {
        return vehiculoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@Valid @RequestBody VehiculoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setModelo(request.getModelo());
        vehiculo.setUsuario(usuario);
        return ResponseEntity.ok(vehiculoRepository.save(vehiculo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(
        @PathVariable Long id,
        @Valid @RequestBody VehiculoRequest request
    ) {
        return vehiculoRepository.findById(id)
            .map(vehiculo -> {
                Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                vehiculo.setModelo(request.getModelo());
                vehiculo.setUsuario(usuario);
                return ResponseEntity.ok(vehiculoRepository.save(vehiculo));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        if (!vehiculoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        vehiculoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}