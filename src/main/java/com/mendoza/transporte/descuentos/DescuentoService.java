package com.mendoza.transporte.descuentos;


import com.mendoza.transporte.choferes.Chofer;
import com.mendoza.transporte.choferes.ChoferDTO;
import com.mendoza.transporte.choferes.ChoferRepository;
import com.mendoza.transporte.empleados.Empleado;
import com.mendoza.transporte.empleados.EmpleadoDTO;
import com.mendoza.transporte.empleados.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public DescuentoResponse createDescuento(DescuentoRequest request) {
        Chofer chofer = choferRepository.findById(request.getIdChofer())
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Descuento descuento = Descuento.builder()
                .chofer(chofer)
                .empleado(empleado)
                .soles(request.getSoles())
                .mensaje(request.getMensaje())
                .imagenUrl(request.getImagenUrl())
                .build();

        Descuento saved = descuentoRepository.save(descuento);

        return toDescuentoResponse(saved, chofer, empleado);
    }

    private DescuentoResponse toDescuentoResponse(Descuento descuento, Chofer chofer, Empleado empleado) {
        return DescuentoResponse.builder()
                .id(descuento.getId())
                .chofer(ChoferDTO.builder()
                        .id(chofer.getId())
                        .nombreCompleto(chofer.getNombreCompleto())
                        .build())
                .empleado(EmpleadoDTO.builder()
                        .id(empleado.getId())
                        .nombreCompleto(empleado.getNombreCompleto())
                        .build())
                .soles(descuento.getSoles())
                .mensaje(descuento.getMensaje())
                .imagenUrl(descuento.getImagenUrl())
                .fechaHora(descuento.getFechaHora())
                .build();
    }




    public DescuentoResponse getDescuento(Long id) {
        Descuento descuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado"));

        Chofer chofer = descuento.getChofer();
        Empleado empleado = descuento.getEmpleado();

        return toDescuentoResponse(descuento, chofer, empleado);
    }


    // Actualizar descuento
    public DescuentoResponse updateDescuento(Long id, DescuentoRequest request) {
        // Buscar el descuento existente
        Descuento descuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado"));

        // Buscar chofer y empleado por ID
        Chofer chofer = choferRepository.findById(request.getIdChofer())
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Actualizar los campos
        descuento.setChofer(chofer);
        descuento.setEmpleado(empleado);
        descuento.setSoles(request.getSoles());
        descuento.setMensaje(request.getMensaje());
        descuento.setImagenUrl(request.getImagenUrl());

        Descuento actualizado = descuentoRepository.save(descuento);

        return toDescuentoResponse(actualizado, chofer, empleado);
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
            Chofer chofer = descuento.getChofer();  // Directamente accediendo al chofer desde la entidad Descuento
            Empleado empleado = descuento.getEmpleado();  // Directamente accediendo al empleado desde la entidad Descuento

            return toDescuentoResponse(descuento, chofer, empleado);
        }).collect(Collectors.toList());
    }



    public List<DescuentoTotalResponse> getDescuentoTotalPorEmpleado() {
        List<Descuento> descuentos = descuentoRepository.findAll();

        Map<Long, DescuentoTotalResponse> acumuladoPorEmpleado = new HashMap<>();

        for (Descuento d : descuentos) {
            Empleado empleado = d.getEmpleado();
            Long idEmpleado = empleado.getId();
            String nombreEmpleado = empleado.getNombreCompleto();
            BigDecimal soles = d.getSoles();

            acumuladoPorEmpleado.merge(
                    idEmpleado,
                    new DescuentoTotalResponse(idEmpleado, nombreEmpleado, soles),
                    (prev, curr) -> {
                        prev.setTotalSoles(prev.getTotalSoles().add(curr.getTotalSoles()));
                        return prev;
                    }
            );
        }

        return new ArrayList<>(acumuladoPorEmpleado.values());
    }




}
