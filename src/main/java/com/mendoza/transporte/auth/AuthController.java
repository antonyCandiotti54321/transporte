package com.mendoza.transporte.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
public class AuthController {

    private final AuthService authService;

    //responseentity hace la respuesta http codigo de estado, encabezado y cuerpo de respuesta
    @PostMapping(value="auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        System.out.println("Login realizado: usuario=" + request.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value="admin/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        AuthResponse response = authService.register(request);
        System.out.println("Registro realizado: usuario=" + request.getUsername() + ", rol=" + request.getRole());
        return ResponseEntity.ok(response);
    }

}
