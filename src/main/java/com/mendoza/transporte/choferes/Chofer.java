package com.mendoza.transporte.choferes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import com.mendoza.transporte.administradores.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="choferes", uniqueConstraints = { @UniqueConstraint(columnNames = {"username"})})
public class Chofer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Evita problemas con la generación de ID
    Long id;
    @Column(nullable = false, unique = true)
    String username;
    String password;
    @Column(name = "nombre_completo")
    String nombreCompleto;
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(precision = 9, scale = 6, nullable = true)
    private BigDecimal latitud;

    @Column(precision = 9, scale = 6, nullable = true)
    private BigDecimal longitud;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); // 🟡 IMPORTANTE para que Spring lo reconozca
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
