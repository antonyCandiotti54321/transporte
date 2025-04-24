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
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Buscamos el usuario, ya sea administrador o chofer
        UserDetails user = administradorRepository
                .findByUsername(request.getUsername())
                .orElseGet(() -> choferRepository
                        .findByUsername(request.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"))
                );

        String token = jwtService.getToken(user);

        // Extraemos los datos comunes de nombre completo y role
        String nombreCompleto = null;
        Role role = null;
        if (user instanceof Administrador) {
            nombreCompleto = ((Administrador) user).getNombreCompleto();
            role = ((Administrador) user).getRole();
        } else if (user instanceof Chofer) {
            nombreCompleto = ((Chofer) user).getNombreCompleto();
            role = ((Chofer) user).getRole();
        }

        return AuthResponse.builder()
                .token(token)
                .nombreCompleto(nombreCompleto)
                .role(role)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == Role.ADMIN) {
            Administrador user = Administrador.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nombreCompleto(request.getNombreCompleto())
                    .role(request.getRole())
                    .build();
            administradorRepository.save(user);

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .nombreCompleto(user.getNombreCompleto())
                    .role(user.getRole())
                    .build();
        }

        Chofer user = Chofer.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombreCompleto(request.getNombreCompleto())
                .role(request.getRole())
                .build();
        choferRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .nombreCompleto(user.getNombreCompleto())
                .role(user.getRole())
                .build();
    }
}
