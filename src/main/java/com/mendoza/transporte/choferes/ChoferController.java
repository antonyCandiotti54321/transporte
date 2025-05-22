package com.mendoza.transporte.choferes;

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
public class ChoferController {

    private final ChoferService choferService;

    @GetMapping
    public ResponseEntity<List<ChoferResponse>> getAll() {
        return ResponseEntity.ok(choferService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChoferResponse> getById(@PathVariable  @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(choferService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChoferResponse> update(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id, @Valid
            @RequestBody  ChoferRequest request
    ) {
        return ResponseEntity.ok(choferService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        choferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
