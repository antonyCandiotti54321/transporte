package com.mendoza.transporte.choferes;

import com.mendoza.transporte.administradores.Role;
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
public class ChoferRequest {
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 20, message = "El username debe tener entre 3 y 20 caracteres")
    private String username;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 3, max = 20, message = "La contraseña debe tener entre 3 y 20 caracteres")
    private String password; // opcional si no se quiere cambiar
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre completo debe tener entre 3 y 20 caracteres")
    private String nombreCompleto;
}
