package com.mendoza.transporte.choferes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChoferService {

    private final ChoferRepository choferRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ChoferResponse> getAll() {
        return choferRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ChoferResponse getById(Long id) {
        Chofer chofer = choferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));
        return toResponse(chofer);
    }

    public ChoferResponse update(Long id, ChoferRequest request) {
        Chofer chofer = choferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        chofer.setNombreCompleto(request.getNombreCompleto());
        chofer.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            chofer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        choferRepository.save(chofer);
        return toResponse(chofer);
    }

    public void delete(Long id) {
        choferRepository.deleteById(id);
    }

    private ChoferResponse toResponse(Chofer chofer) {
        return ChoferResponse.builder()
                .id(chofer.getId())
                .username(chofer.getUsername())
                .nombreCompleto(chofer.getNombreCompleto())
                .build();
    }
}
