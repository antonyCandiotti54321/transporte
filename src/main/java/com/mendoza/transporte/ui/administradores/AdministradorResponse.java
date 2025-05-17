package com.mendoza.transporte.ui.administradores;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdministradorResponse {
    private Long id;
    private String username;
    private String nombreCompleto;
}
