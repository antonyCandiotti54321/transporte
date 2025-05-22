package com.mendoza.transporte.auth;

import com.mendoza.transporte.administradores.Role;
import com.mendoza.transporte.administradores.ValidEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 20, message = "El username debe tener entre 3 y 20 caracteres")
   String username;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 3, max = 20, message = "La contraseña debe tener entre 3 y 20 caracteres")
    String password;
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre completo debe tener entre 3 y 20 caracteres")
    String nombreCompleto;
    @NotNull(message = "El rol es obligatorio")
    @ValidEnum(enumClass = Role.class, message = "Role inválido. Debe ser ADMIN o CHOFER")
    Role role;

}
