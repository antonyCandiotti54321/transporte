package com.mendoza.transporte.healthCheck;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthCheckController {
    @Operation(summary = "HealthCheck", description = "Hace que el backend no se apague, manda una peticion cada 5 min")
    @GetMapping("api/health")
    public ResponseEntity<String> healthCheck() {

        System.out.println("OK healthcheck");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("OK PING");
        return ResponseEntity.ok("OK");
    }


}
