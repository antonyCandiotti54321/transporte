package com.mendoza.transporte.ui.empleados;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoResponse {
    Long id;
    String nombreCompleto;
}
