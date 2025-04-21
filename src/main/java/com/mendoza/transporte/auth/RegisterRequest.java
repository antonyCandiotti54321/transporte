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
public class RegisterRequest {
    String username;
    String password;
    String nombreCompleto;
    Role role;
}
