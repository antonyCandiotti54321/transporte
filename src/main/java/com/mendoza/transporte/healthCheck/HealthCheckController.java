package com.mendoza.transporte.healthCheck;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Sistema", description = "Operaciones generales del sistema")
public class HealthCheckController {

    @Operation(summary = "Verificar estado del sistema", description = "Comprueba el estado para evitar que el sistema entre en modo sleep.")
    @GetMapping("api/health")
    public ResponseEntity<String> healthCheck() {

        System.out.println("OK healthcheck");
        return ResponseEntity.ok("OK");
    }

    @Hidden
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("OK PING");
        return ResponseEntity.ok("OK");
    }


}
