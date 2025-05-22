package com.mendoza.transporte.empleados;

import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.AuthService;
import com.mendoza.transporte.auth.RegisterRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping("admins/empleados")
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody EmpleadoRequest request) {
        Empleado creado = empleadoService.createEmpleado(request);
        System.out.println("Empleado creado: " + creado);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("empleados")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        // Imprimir empleados en consola
        System.out.println("Lista de empleados:");
        empleados.forEach(e -> System.out.println("- " + e.getId() + ": " + e.getNombreCompleto()));

        return ResponseEntity.ok(empleados);
    }

    @DeleteMapping("admins/empleados/{id}")
    public void eliminar(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        empleadoService.eliminar(id);
        System.out.println("Empleado eliminado");
    }

    @PutMapping("admins/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable @Positive(message = "El id debe ser positivo") Long id, @Valid @RequestBody EmpleadoRequest request) {
        Empleado actualizado = empleadoService.updateEmpleado(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        Empleado empleado = empleadoService.getEmpleadoById(id);
        return ResponseEntity.ok(empleado);
    }


}
