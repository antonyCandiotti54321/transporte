package com.mendoza.transporte.descuentos;

import com.mendoza.transporte.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/chofer/descuentos")
@RequiredArgsConstructor
public class DescuentoController {

    private final AuthService authService;
    private final DescuentoService descuentoService;

    @PostMapping
    public ResponseEntity<Descuento> createDescuento(@RequestBody DescuentoRequest request) {
        Descuento creado = descuentoService.createDescuento(request);
        System.out.println("createDescuento response: " + creado);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDescuento(@PathVariable Long id) {
        DescuentoResponse response = descuentoService.getDescuento(id);

        if (response == null) {
            String mensaje = "Descuento no encontrado con ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }

        System.out.println("getDescuento response (ID=" + id + "): " + response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDescuento(@PathVariable Long id,
                                                  @RequestBody DescuentoRequest request) {
        Descuento actualizado = descuentoService.updateDescuento(id, request);
        ResponseEntity<Object> response;
        if (actualizado == null) {
            String mensaje = "Descuento no encontrado con ID: " + id;
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        } else {
            response = ResponseEntity.ok(actualizado);
        }
        System.out.println("updateDescuento response (ID=" + id + "): " + response.getBody());
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDescuento(@PathVariable Long id) {
        boolean deleted = descuentoService.deleteDescuento(id);
        ResponseEntity<Object> response;
        if (!deleted) {
            String mensaje = "Descuento no encontrado con ID: " + id;
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        } else {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        System.out.println("deleteDescuento response (ID=" + id + "): status=" + response.getStatusCode());
        return response;
    }




    @GetMapping
    public ResponseEntity<Map<String, Object>> getDescuentosPaginados(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DescuentoResponse> descuentosPage = descuentoService.getDescuentosPaginados(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("descuentos", descuentosPage.getContent());
        response.put("currentPage", descuentosPage.getNumber() + 1);
        response.put("totalItems", descuentosPage.getTotalElements());
        response.put("totalPages", descuentosPage.getTotalPages());

        System.out.println("getDescuentosPaginados response: " +
                "page=" + (descuentosPage.getNumber() + 1) +
                ", size=" + descuentosPage.getSize() +
                ", totalItems=" + descuentosPage.getTotalElements() +
                ", totalPages=" + descuentosPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}
