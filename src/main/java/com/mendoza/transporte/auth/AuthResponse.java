package com.mendoza.transporte.auth;

import com.mendoza.transporte.administradores.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    String token;
    String nombreCompleto;
    Role role;

}
