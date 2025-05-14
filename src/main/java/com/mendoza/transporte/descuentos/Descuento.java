package com.mendoza.transporte.descuentos;

import com.mendoza.transporte.choferes.Chofer;
import com.mendoza.transporte.empleados.Empleado;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "descuento_seq")
    @SequenceGenerator(name = "descuento_seq", sequenceName = "descuento_seq", allocationSize = 1)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chofer", nullable = false)
    Chofer chofer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    Empleado empleado;
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
