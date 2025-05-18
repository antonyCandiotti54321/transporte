package com.mendoza.transporte.ubicacion;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionDTO {
    private Long id;        // ID del camión o celular
    private double latitud;
    private double longitud;
}