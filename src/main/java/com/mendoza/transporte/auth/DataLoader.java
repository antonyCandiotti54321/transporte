package com.mendoza.transporte.auth;

import com.mendoza.transporte.administradores.AdministradorRepository;
import com.mendoza.transporte.choferes.ChoferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.mendoza.transporte.administradores.Role;

@RequiredArgsConstructor
@Component
public class DataLoader {

    private final AdministradorRepository administradorRepository;
    private final AuthService authService;
    private final ChoferRepository choferRepository;

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
            }else {
                System.out.println("Administrador ya existe.");
            }

            // Crear chofer si no existe
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
        };
    }
}
