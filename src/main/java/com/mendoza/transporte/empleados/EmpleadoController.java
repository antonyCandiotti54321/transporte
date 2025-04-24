package com.mendoza.transporte.empleados;

import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.AuthService;
import com.mendoza.transporte.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping("admin/register/empleado")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody EmpleadoRequest request) {
        Empleado creado = empleadoService.createEmpleado(request);
        System.out.println("Empleado creado: " + creado);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("admin/empleados")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        // Imprimir empleados en consola
        System.out.println("Lista de empleados:");
        empleados.forEach(e -> System.out.println("- " + e.getId() + ": " + e.getNombreCompleto()));

        return ResponseEntity.ok(empleados);
    }

}
