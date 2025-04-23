package com.mendoza.transporte.descuentos;

import jakarta.persistence.*;
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
@Entity
@Table(name="descuentos")
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Evita problemas con la generación de ID
    Long id;
    @Column(nullable = false)
    Long idChofer;
    @Column(nullable = false)
    Long idEmpleado;
    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal soles;
    String mensaje;
    @Column(name = "imagen_url")
    String imagenUrl;
    @Column(name = "fecha_hora", nullable = false)
    LocalDateTime fechaHora;

    // Se ejecuta antes de insertar un nuevo registro
    @PrePersist
    protected void onCreate() {
        this.fechaHora = LocalDateTime.now();
    }

    // Se ejecuta antes de actualizar un registro existente
    @PreUpdate
    protected void onUpdate() {
        this.fechaHora = LocalDateTime.now();
    }
}
