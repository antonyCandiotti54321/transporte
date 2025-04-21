package com.mendoza.transporte.descuentos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DescuentoRepository extends JpaRepository<Descuento, Long> {
    Page<Descuento> findAll(Pageable pageable);
}
