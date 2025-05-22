package com.mendoza.transporte.descuentos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "El id del chofer es obligatorio")
    @Positive(message = "El id del chofer debe ser positivo")
    private Long idChofer;
    @NotNull(message = "El id del empleado es obligatorio")
    @Positive(message = "El id del empleado debe ser positivo")
    private Long idEmpleado;
    @NotNull(message = "El monto en soles es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private BigDecimal soles;
    @Size(max = 255, message = "El mensaje no debe superar los 255 caracteres")
    private String mensaje;
    @Size(max = 255, message = "La URL de la imagen no debe superar los 255 caracteres")
    private String imagenUrl;
}
