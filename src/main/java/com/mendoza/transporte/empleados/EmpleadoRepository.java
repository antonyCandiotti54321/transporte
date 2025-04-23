package com.mendoza.transporte.empleados;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByNombreCompleto(String nombreCompleto);
}