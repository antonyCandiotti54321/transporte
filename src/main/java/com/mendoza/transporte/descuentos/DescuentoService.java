package com.mendoza.transporte.descuentos;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;

    public Descuento createDescuento(DescuentoResponse request){
    Descuento descuento = Descuento.builder()
            .idUsuario(request.getIdUsuario())
            .idEmpleado(request.getIdEmpleado())
            .soles(request.getSoles())
            .mensaje(request.getMensaje())
            .imagenUrl(request.getImagenUrl())
            .build();
        return descuentoRepository.save(descuento);

        }

    public Descuento getDescuento(Long id){
        return descuentoRepository.findById(id).orElse(null);
    }

    // Actualizar descuento
    public Descuento updateDescuento(Long id, DescuentoResponse request) {
        // Buscar el descuento por ID
        Descuento descuento = descuentoRepository.findById(id).orElse(null);
        if (descuento != null) {
            // Actualizar los campos
            descuento.setIdUsuario(request.getIdUsuario());
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


    public Page<Descuento> getDescuentosPaginados(Pageable pageable) {
        return descuentoRepository.findAll(pageable);
    }



}
