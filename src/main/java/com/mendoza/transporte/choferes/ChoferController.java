package com.mendoza.transporte.choferes;

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
    public ResponseEntity<ChoferResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(choferService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChoferResponse> update(
            @PathVariable Long id,
            @RequestBody ChoferRequest request
    ) {
        return ResponseEntity.ok(choferService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        choferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
