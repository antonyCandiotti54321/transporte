package com.mendoza.transporte.descuentos;


import com.mendoza.transporte.choferes.Chofer;
import com.mendoza.transporte.choferes.ChoferRepository;
import com.mendoza.transporte.empleados.Empleado;
import com.mendoza.transporte.empleados.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;
    private final ChoferRepository choferRepository;
    private final EmpleadoRepository empleadoRepository;

    public Descuento createDescuento(DescuentoRequest request){
    Descuento descuento = Descuento.builder()
            .idChofer(request.getIdChofer())
            .idEmpleado(request.getIdEmpleado())
            .soles(request.getSoles())
            .mensaje(request.getMensaje())
            .imagenUrl(request.getImagenUrl())
            .build();
        return descuentoRepository.save(descuento);

        }

    public DescuentoResponse getDescuento(Long id) {
        Descuento descuento = descuentoRepository.findById(id).orElse(null);
        if (descuento == null) return null;

        Chofer chofer = choferRepository.findById(descuento.getIdChofer())
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        Empleado empleado = empleadoRepository.findById(descuento.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        return DescuentoResponse.builder()
                .id(descuento.getId())
                .nombreChofer(chofer.getNombreCompleto())
                .nombreEmpleado(empleado.getNombreCompleto())
                .soles(descuento.getSoles())
                .mensaje(descuento.getMensaje())
                .imagenUrl(descuento.getImagenUrl())
                .fechaHora(descuento.getFechaHora())
                .build();
    }

    // Actualizar descuento
    public Descuento updateDescuento(Long id, DescuentoRequest request) {
        // Buscar el descuento por ID
        Descuento descuento = descuentoRepository.findById(id).orElse(null);
        if (descuento != null) {
            // Actualizar los campos
            descuento.setIdChofer(request.getIdChofer());
            descuento.setIdEmpleado(request.getIdEmpleado());
            descuento.setSoles(request.getSoles());
            descuento.setMensaje(request.getMensaje());
            descuento.setImagenUrl(request.getImagenUrl());
            return descuentoRepository.save(descuento);
        }
        return null; // Si no se encuentra, devolver null
    }

    // Eliminar descuento
    public boolean deleteDescuento(Long id) {
        // Verificar si el descuento existe
        if (descuentoRepository.existsById(id)) {
            descuentoRepository.deleteById(id);
            return true;
        }
        return false; // Si no existe, devolver false
    }


    public List<DescuentoResponse> getTodosLosDescuentos() {
        List<Descuento> descuentos = descuentoRepository.findAll();

        return descuentos.stream().map(descuento -> {
            Chofer chofer = choferRepository.findById(descuento.getIdChofer())
                    .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

            Empleado empleado = empleadoRepository.findById(descuento.getIdEmpleado())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            return DescuentoResponse.builder()
                    .id(descuento.getId())
                    .nombreChofer(chofer.getNombreCompleto())
                    .nombreEmpleado(empleado.getNombreCompleto())
                    .soles(descuento.getSoles())
                    .mensaje(descuento.getMensaje())
                    .imagenUrl(descuento.getImagenUrl())
                    .fechaHora(descuento.getFechaHora())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<DescuentoTotalResponse> getDescuentoTotalPorEmpleado() {
        List<Descuento> descuentos = descuentoRepository.findAll();

        Map<Long, BigDecimal> sumaPorEmpleado = new HashMap<>();

        for (Descuento d : descuentos) {
            sumaPorEmpleado.merge(
                    d.getIdEmpleado(),
                    d.getSoles(),
                    BigDecimal::add
            );
        }

        return sumaPorEmpleado.entrySet().stream().map(entry -> {
            Long idEmpleado = entry.getKey();
            BigDecimal totalSoles = entry.getValue();

            String nombreEmpleado = empleadoRepository.findById(idEmpleado)
                    .map(Empleado::getNombreCompleto)
                    .orElse("Desconocido");

            return new DescuentoTotalResponse(idEmpleado, nombreEmpleado, totalSoles);
        }).collect(Collectors.toList());

    }



}
