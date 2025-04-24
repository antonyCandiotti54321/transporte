package com.mendoza.transporte.empleados;


import com.mendoza.transporte.administradores.Administrador;
import com.mendoza.transporte.administradores.Role;
import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.RegisterRequest;
import com.mendoza.transporte.choferes.Chofer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
// evita el error de  private final UserRepository userRepository; y inicia su constructor automaticamente
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public Empleado createEmpleado(EmpleadoRequest request) {
        Empleado empleado = Empleado.builder()
                .nombreCompleto(request.getNombreCompleto())
                .build();
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

}
