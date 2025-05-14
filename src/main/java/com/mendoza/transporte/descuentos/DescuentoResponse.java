package com.mendoza.transporte.descuentos;

import com.mendoza.transporte.choferes.ChoferDTO;
import com.mendoza.transporte.empleados.EmpleadoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DescuentoResponse {
    private Long id;
    private ChoferDTO chofer;
    private EmpleadoDTO empleado;
    private BigDecimal soles;
    private String mensaje;
    private String imagenUrl;
    private LocalDateTime fechaHora;
}
