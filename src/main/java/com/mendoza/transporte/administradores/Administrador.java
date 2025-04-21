package com.mendoza.transporte.administradores;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="administradores", uniqueConstraints = { @UniqueConstraint(columnNames = {"username"})})
public class Administrador implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); // 🔥 Importante: Spring espera "ROLE_" por defecto
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
