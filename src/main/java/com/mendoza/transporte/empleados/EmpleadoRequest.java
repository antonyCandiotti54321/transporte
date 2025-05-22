package com.mendoza.transporte.empleados;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRequest {
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre completo debe tener entre 3 y 20 caracteres")
    private String nombreCompleto;
}
