package com.mendoza.transporte.choferes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChoferDTO {
    Long id;
    String nombreCompleto;
}
