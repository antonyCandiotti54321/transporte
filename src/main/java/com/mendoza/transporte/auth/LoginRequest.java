package com.mendoza.transporte.auth;

import com.mendoza.transporte.administradores.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//pedir credenciales
//DATA: getter setter automaticos
// BUILDER: construir objetos
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String username;
    String password;

}
