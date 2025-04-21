package com.mendoza.transporte.descuentos;


import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.AuthService;
import com.mendoza.transporte.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RestController
@RequestMapping("api/chofer/descuentos")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
public class DescuentoController {

    private final AuthService authService;
    private final DescuentoService descuentoService ;

    //responseentity hace la respuesta http codigo de estado, encabezado y cuerpo de respuesta
    @PostMapping(value="crea")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping
    public ResponseEntity<Descuento> createDescuento(@RequestBody DescuentoResponse request){
        return ResponseEntity.ok(descuentoService.createDescuento(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDescuento(@PathVariable Long id){
        Descuento descuento = descuentoService.getDescuento(id);
        if (descuento == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Descuento no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(descuento);
    }

    // Actualizar descuento
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDescuento(@PathVariable Long id, @RequestBody DescuentoResponse request) {
        Descuento descuento = descuentoService.updateDescuento(id, request);
        if (descuento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Descuento no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(descuento);
    }

    // Eliminar descuento
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDescuento(@PathVariable Long id) {
        boolean deleted = descuentoService.deleteDescuento(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Descuento no encontrado con ID: " + id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<Descuento>> getDescuentosPaginados(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Descuento> descuentos = descuentoService.getDescuentosPaginados(pageable);
        return ResponseEntity.ok(descuentos);
    }


}
