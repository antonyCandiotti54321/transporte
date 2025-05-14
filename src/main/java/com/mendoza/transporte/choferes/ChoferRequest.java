package com.mendoza.transporte.choferes;

import com.mendoza.transporte.administradores.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoferRequest {
    private String username;
    private String password; // opcional si no se quiere cambiar
    private String nombreCompleto;
}
