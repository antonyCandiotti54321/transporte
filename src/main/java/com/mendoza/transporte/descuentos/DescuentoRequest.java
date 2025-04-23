package com.mendoza.transporte.descuentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DescuentoRequest {
    private Long idChofer;
    private Long idEmpleado;
    private BigDecimal soles;
    private String mensaje;
    private String imagenUrl;
}
