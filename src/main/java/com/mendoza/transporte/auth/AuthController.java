package com.mendoza.transporte.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
@Tag(name = "Autenticación", description = "Operaciones relacionadas con autenticación y registro")
public class AuthController {

    private final AuthService authService;

    //responseentity hace la respuesta http codigo de estado, encabezado y cuerpo de respuesta
    @Operation(summary = "Login usuario", description = "Autentica un usuario con username y password.")
    @PostMapping(value="auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        System.out.println("Login realizado: usuario=" + request.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario, ya sea admin o chofer.")
    @PostMapping(value="admins/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);
        System.out.println("Registro realizado: usuario=" + request.getUsername() + ", rol=" + request.getRole());
        return ResponseEntity.ok(response);
    }

}
