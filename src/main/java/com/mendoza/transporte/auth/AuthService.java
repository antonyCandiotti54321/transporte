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

import java.util.Optional;

@Service
@RequiredArgsConstructor // evita el error de  private final UserRepository userRepository; y inicia su constructor automaticamente
public class AuthService {

    private final AdministradorRepository administradorRepository;
    private final ChoferRepository choferRepository;
    private final JwtService jwtService; // se usa esta variable para el getToken porque no se puede hacer como static
    private final PasswordEncoder passwordEncoder; // se usa para hacer la encroptacion
    //Login
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        System.out.println("Se esta creando la llave de prueba");
        try {
            System.out.println(request.getUsername() +" PRUEBA " + request.getPassword() );
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        UserDetails admin = administradorRepository.findByUsername(request.getUsername()).orElse(null);//regresa un null si no lo encuentra
        if (admin == null) {
            admin = choferRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        }
        String token = jwtService.getToken(admin);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        if (request.getRole() == Role.ADMIN){
            Administrador user = Administrador.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()) )
                    .nombreCompleto(request.getNombreCompleto())
                    .role(request.getRole())
                    .build();
            administradorRepository.save(user);

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
        }
            Chofer user = Chofer.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()) )
                    .nombreCompleto(request.getNombreCompleto())
                    .role(request.getRole())
                    .build();

        choferRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
