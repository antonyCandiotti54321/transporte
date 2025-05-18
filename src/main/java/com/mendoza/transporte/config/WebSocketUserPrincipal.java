package com.mendoza.transporte.config;

import com.mendoza.transporte.administradores.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@Builder
@AllArgsConstructor
public class WebSocketUserPrincipal implements Principal {
    private final String name;
    private final String role;
}
