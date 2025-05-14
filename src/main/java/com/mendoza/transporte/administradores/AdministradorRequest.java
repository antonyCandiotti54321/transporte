package com.mendoza.transporte.administradores;

import com.mendoza.transporte.administradores.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorRequest {
    private String username;
    private String password; // opcional si no se quiere cambiar
    private String nombreCompleto;
}
