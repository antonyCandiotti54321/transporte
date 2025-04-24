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
import java.util.List;

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
    public ResponseEntity<List<DescuentoResponse>> getTodosLosDescuentos() {
        List<DescuentoResponse> descuentos = descuentoService.getTodosLosDescuentos();
        return ResponseEntity.ok(descuentos);
    }

    @GetMapping("/descuento-total")
    public ResponseEntity<List<DescuentoTotalResponse>> getDescuentoTotalPorEmpleado() {
        List<DescuentoTotalResponse> resultado = descuentoService.getDescuentoTotalPorEmpleado();
        return ResponseEntity.ok(resultado);
    }


}
