package com.proyecto.arley.controller;

import com.proyecto.arley.Service.UsuarioService;
import com.proyecto.arley.Entity.Usuario;
import com.proyecto.arley.dto.UsuarioHistorialDTO;
import com.proyecto.arley.dto.UsuarioRegistroRequest;
import com.proyecto.arley.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRegistroRequest request) {
        UsuarioResponseDTO nuevoUsuario = usuarioService.registrarUsuario(request);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<UsuarioHistorialDTO>> obtenerHistorial(@RequestParam(required = false) String nombre) {
        List<UsuarioHistorialDTO> historial = usuarioService.obtenerHistorialPorNombre(nombre);
        return ResponseEntity.ok(historial);
    }

    // ➕ ENDPOINT FALTANTE: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRegistroRequest request) {
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // ➕ ENDPOINT FALTANTE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario y sus vehículos eliminados correctamente.");
    }
}