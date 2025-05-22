package com.mendoza.transporte.choferes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/choferes")
@RequiredArgsConstructor
@Tag(name = "Choferes", description = "Operaciones relacionadas con Choferes")
public class ChoferController {

    private final ChoferService choferService;

    @Operation(summary = "Listar choferes", description = "Devuelve la lista de todos los choferes.")
    @GetMapping
    public ResponseEntity<List<ChoferResponse>> getAll() {
        return ResponseEntity.ok(choferService.getAll());
    }

    @Operation(summary = "Obtener chofer por ID", description = "Devuelve los datos de un chofer específico.")
    @GetMapping("/{id}")
    public ResponseEntity<ChoferResponse> getById(@PathVariable  @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(choferService.getById(id));
    }

    @Operation(summary = "Actualizar chofer", description = "Actualiza los datos de un chofer específico.")
    @PutMapping("/{id}")
    public ResponseEntity<ChoferResponse> update(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id, @Valid
            @RequestBody  ChoferRequest request
    ) {
        return ResponseEntity.ok(choferService.update(id, request));
    }

    @Operation(summary = "Eliminar chofer", description = "Elimina un chofer por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        choferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
