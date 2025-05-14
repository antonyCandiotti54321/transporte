package com.mendoza.transporte.administradores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AdministradorResponse> getAll() {
        return administradorRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AdministradorResponse getById(Long id) {
        Administrador admin = administradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        return toResponse(admin);
    }

    public AdministradorResponse update(Long id, AdministradorRequest request) {
        Administrador admin = administradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        admin.setNombreCompleto(request.getNombreCompleto());
        admin.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        administradorRepository.save(admin);
        return toResponse(admin);
    }

    public void delete(Long id) {
        administradorRepository.deleteById(id);
    }

    private AdministradorResponse toResponse(Administrador admin) {
        return AdministradorResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .nombreCompleto(admin.getNombreCompleto())
                .build(); // ❌ .role(admin.getRole()) se elimina si no lo usas
    }

}
