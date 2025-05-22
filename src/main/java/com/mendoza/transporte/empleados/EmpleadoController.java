package com.mendoza.transporte.empleados;

import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.AuthService;
import com.mendoza.transporte.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
@Tag(name = "Empleados", description = "Operaciones relacionadas con Empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Operation(summary = "Crear empleado", description = "Registra un nuevo empleado.")
    @PostMapping("admins/empleados")
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody EmpleadoRequest request) {
        Empleado creado = empleadoService.createEmpleado(request);
        System.out.println("Empleado creado: " + creado);
        return ResponseEntity.ok(creado);
    }

    @Operation(summary = "Listar empleados", description = "Devuelve la lista de todos los empleados.")
    @GetMapping("empleados")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        // Imprimir empleados en consola
        System.out.println("Lista de empleados:");
        empleados.forEach(e -> System.out.println("- " + e.getId() + ": " + e.getNombreCompleto()));

        return ResponseEntity.ok(empleados);
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado por su ID.")
    @DeleteMapping("admins/empleados/{id}")
    public void eliminar(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        empleadoService.eliminar(id);
        System.out.println("Empleado eliminado");
    }

    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado específico.")
    @PutMapping("admins/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable @Positive(message = "El id debe ser positivo") Long id, @Valid @RequestBody EmpleadoRequest request) {
        Empleado actualizado = empleadoService.updateEmpleado(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Obtener empleado por ID", description = "Devuelve los datos de un empleado específico.")
    @GetMapping("empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        Empleado empleado = empleadoService.getEmpleadoById(id);
        return ResponseEntity.ok(empleado);
    }


}
