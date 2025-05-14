package com.mendoza.transporte.descuentos;

import com.mendoza.transporte.auth.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class DescuentoController {

    private final DescuentoService descuentoService;

    @PostMapping("/chofer/descuentos")
    public ResponseEntity<DescuentoResponse> createDescuento(@RequestBody DescuentoRequest request) {
        DescuentoResponse creado = descuentoService.createDescuento(request);
        System.out.println("createDescuento response: " + creado);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("chofer/descuentos/{id}")
    public ResponseEntity<?> getDescuento(@PathVariable Long id) {
        try {
            DescuentoResponse response = descuentoService.getDescuento(id);
            System.out.println("getDescuento response (ID=" + id + "): " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            String mensaje = "Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
    }

    @PutMapping("chofer/descuentos/{id}")
    public ResponseEntity<Object> updateDescuento(@PathVariable Long id,
                                                  @RequestBody DescuentoRequest request) {
        try {
            DescuentoResponse actualizado = descuentoService.updateDescuento(id, request);
            System.out.println("updateDescuento response (ID=" + id + "): " + actualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            String mensaje = "Error al actualizar descuento: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
    }


    @DeleteMapping("chofer/descuentos/{id}")
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


    @GetMapping("chofer/descuentos")
    public ResponseEntity<List<DescuentoResponse>> getTodosLosDescuentos() {
        List<DescuentoResponse> descuentos = descuentoService.getTodosLosDescuentos();

        if (descuentos.isEmpty()) {
            return ResponseEntity.noContent().build();  // Retorna 204 si no hay descuentos
        }

        return ResponseEntity.ok(descuentos);  // Retorna los descuentos con 200 OK
    }


    @GetMapping("admin/descuentos/descuento-total")
    public ResponseEntity<List<DescuentoTotalResponse>> getDescuentoTotalPorEmpleado() {
        List<DescuentoTotalResponse> resultado = descuentoService.getDescuentoTotalPorEmpleado();
        return ResponseEntity.ok(resultado);
    }


}
