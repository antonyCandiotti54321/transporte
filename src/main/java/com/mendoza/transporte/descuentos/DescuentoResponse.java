package com.mendoza.transporte.descuentos;

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
    Long id;
    Long idUsuario;
    Long idEmpleado;
    BigDecimal soles;
    String mensaje;
    String imagenUrl;
    LocalDateTime fechaHora;
}
