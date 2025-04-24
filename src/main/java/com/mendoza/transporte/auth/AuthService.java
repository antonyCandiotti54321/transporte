package com.mendoza.transporte.auth;

import com.mendoza.transporte.administradores.Role;
import com.mendoza.transporte.choferes.Chofer;
import com.mendoza.transporte.choferes.ChoferRepository;
import com.mendoza.transporte.jwt.JwtService;
import com.mendoza.transporte.administradores.Administrador;
import com.mendoza.transporte.administradores.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdministradorRepository administradorRepository;
    private final ChoferRepository choferRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // 1. Autenticación
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // 2. Cargar usuario (administrador o chofer)
        UserDetails user;
        var optAdmin = administradorRepository.findByUsername(request.getUsername());
        if (optAdmin.isPresent()) {
            user = optAdmin.get();
        } else {
            user = choferRepository
                    .findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        }

        // 3. Generar token
        String token = jwtService.getToken(user);

        // 4. Extraer nombreCompleto y role
        String nombreCompleto;
        Role role;
        if (user instanceof Administrador admin) {
            nombreCompleto = admin.getNombreCompleto();
            role = admin.getRole();
        } else {
            Chofer chofer = (Chofer) user;
            nombreCompleto = chofer.getNombreCompleto();
            role = chofer.getRole();
        }

        // 5. Devolver respuesta
        return AuthResponse.builder()
                .token(token)
                .nombreCompleto(nombreCompleto)
                .role(role)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == Role.ADMIN) {
            Administrador admin = Administrador.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nombreCompleto(request.getNombreCompleto())
                    .role(request.getRole())
                    .build();
            administradorRepository.save(admin);

            return AuthResponse.builder()
                    .token(jwtService.getToken(admin))
                    .nombreCompleto(admin.getNombreCompleto())
                    .role(admin.getRole())
                    .build();
        }

        Chofer chofer = Chofer.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombreCompleto(request.getNombreCompleto())
                .role(request.getRole())
                .build();
        choferRepository.save(chofer);

        return AuthResponse.builder()
                .token(jwtService.getToken(chofer))
                .nombreCompleto(chofer.getNombreCompleto())
                .role(chofer.getRole())
                .build();
    }
}
