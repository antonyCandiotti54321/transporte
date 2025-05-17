package com.mendoza.transporte.ui.choferes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChoferResponse {
    private Long id;
    private String username;
    private String nombreCompleto;

}