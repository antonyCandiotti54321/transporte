package com.mendoza.transporte.administradores;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "Administradores", description = "Operaciones relacionadas con administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    @Operation(summary = "Listar administradores", description = "Devuelve la lista de todos los administradores.")
    @GetMapping
    public ResponseEntity<List<AdministradorResponse>> getAllAdmins() {
        return ResponseEntity.ok(administradorService.getAll());
    }

    @Operation(summary = "Obtener administrador por ID", description = "Devuelve los datos de un administrador específico.")
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponse> getAdminById(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(administradorService.getById(id));
    }

    @Operation(summary = "Actualizar administrador", description = "Actualiza los datos de un administrador específico.")
    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponse> updateAdmin(@PathVariable @Positive(message = "El id debe ser positivo") Long id,@Valid @RequestBody AdministradorRequest request) {
        return ResponseEntity.ok(administradorService.update(id, request));
    }

    @Operation(summary = "Eliminar administrador", description = "Elimina un administrador por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
