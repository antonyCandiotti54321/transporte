package com.mendoza.transporte.empleados;

import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.AuthService;
import com.mendoza.transporte.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("empleado")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        // Imprimir empleados en consola
        System.out.println("Lista de empleados:");
        empleados.forEach(e -> System.out.println("- " + e.getId() + ": " + e.getNombreCompleto()));

        return ResponseEntity.ok(empleados);
    }

    @DeleteMapping("admin/empleado/{id}")
    public void eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        System.out.println("Empleado eliminado");
    }

    @PutMapping("admin/empleado/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoRequest request) {
        Empleado actualizado = empleadoService.updateEmpleado(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("empleado/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        Empleado empleado = empleadoService.getEmpleadoById(id);
        return ResponseEntity.ok(empleado);
    }


}
