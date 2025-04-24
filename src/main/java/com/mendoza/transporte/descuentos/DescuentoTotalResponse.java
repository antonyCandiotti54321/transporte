package com.mendoza.transporte.descuentos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class DescuentoTotalResponse {
    private Long idEmpleado;
    private String nombreEmpleado;
    private BigDecimal totalSoles;

    public DescuentoTotalResponse(Long idEmpleado, String nombreEmpleado, BigDecimal totalSoles) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.totalSoles = totalSoles;
    }

    // Getters y setters o usa @Data si usas Lombok
}

