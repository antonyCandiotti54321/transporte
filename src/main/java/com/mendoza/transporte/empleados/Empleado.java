package com.mendoza.transporte.empleados;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="empleados", uniqueConstraints = { @UniqueConstraint(columnNames = {"nombre_completo"})})
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Evita problemas con la generación de ID
    Long id;
    @Column(name = "nombre_completo", nullable = false, unique = true)
    String nombreCompleto;
}
