package com.mendoza.transporte.choferes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChoferRepository extends JpaRepository<Chofer, Long> {
    Optional<Chofer> findByUsername(String username);
}
