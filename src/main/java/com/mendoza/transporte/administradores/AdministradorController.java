package com.mendoza.transporte.administradores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @GetMapping
    public ResponseEntity<List<AdministradorResponse>> getAllAdmins() {
        return ResponseEntity.ok(administradorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponse> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponse> updateAdmin(@PathVariable Long id, @RequestBody AdministradorRequest request) {
        return ResponseEntity.ok(administradorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
