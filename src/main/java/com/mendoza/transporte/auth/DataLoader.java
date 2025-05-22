package com.mendoza.transporte.auth;

import com.mendoza.transporte.administradores.AdministradorRepository;
import com.mendoza.transporte.choferes.ChoferRepository;
import com.mendoza.transporte.administradores.Role;
import com.mendoza.transporte.empleados.Empleado;
import com.mendoza.transporte.empleados.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader {

    private final AdministradorRepository administradorRepository;
    private final AuthService authService;
    private final ChoferRepository choferRepository;
    private final EmpleadoRepository empleadoRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            if (administradorRepository.findByUsername("admin").isEmpty()) {
                RegisterRequest request = RegisterRequest.builder()
                        .username("admin")
                        .password("admin")
                        .nombreCompleto("Administrador prueba")
                        .role(Role.ADMIN)
                        .build();
                authService.register(request);
                System.out.println("Usuario administrador creado con exito.");
            } else {
                System.out.println("Administrador ya existe.");
            }

            if (choferRepository.findByUsername("chofer").isEmpty()) {
                RegisterRequest request = RegisterRequest.builder()
                        .username("chofer")
                        .password("chofer")
                        .nombreCompleto("Chofer prueba")
                        .role(Role.CHOFER)
                        .build();
                authService.register(request);
                System.out.println("Chofer creado con exito.");
            } else {
                System.out.println("Chofer ya existe.");
            }

            // Nuevo chofer: Kelly
            if (choferRepository.findByUsername("kelly").isEmpty()) {
                RegisterRequest request = RegisterRequest.builder()
                        .username("kelly")
                        .password("kelly")
                        .nombreCompleto("Kelly Uwarai")
                        .role(Role.CHOFER)
                        .build();
                authService.register(request);
                System.out.println("Chofer 'Kelly' creado con exito.");
            } else {
                System.out.println("Chofer 'Kelly' ya existe.");
            }

            // Nuevo chofer: Profesor
            if (choferRepository.findByUsername("profesor").isEmpty()) {
                RegisterRequest request = RegisterRequest.builder()
                        .username("profesor")
                        .password("profesor")
                        .nombreCompleto("Profesor Senati")
                        .role(Role.CHOFER)
                        .build();
                authService.register(request);
                System.out.println("Chofer 'Profesor' creado con exito.");
            } else {
                System.out.println("Chofer 'Profesor' ya existe.");
            }

            // Crear empleado por defecto si no existe
            String nombreEmpleado = "Empleado prueba";
            if (empleadoRepository.findByNombreCompleto(nombreEmpleado).isEmpty()) {
                Empleado empleado = Empleado.builder()
                        .nombreCompleto(nombreEmpleado)
                        .build();
                empleadoRepository.save(empleado);
                System.out.println("Empleado creado con exito.");
            } else {
                System.out.println("Empleado ya existe.");
            }
        };
    }
}
