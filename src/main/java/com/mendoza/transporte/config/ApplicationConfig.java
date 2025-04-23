package com.mendoza.transporte.config;

import com.mendoza.transporte.administradores.AdministradorRepository;
import com.mendoza.transporte.choferes.ChoferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AdministradorRepository administradorRepository;
    private final ChoferRepository choferRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Ahora busca primero en administradores y si no está,
     * va a choferes. Así podrás autenticar a ambos tipos.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Busca en administradores
            return administradorRepository.findByUsername(username)
                    .<UserDetails>map(a -> a)
                    // Si no está, busca en choferes
                    .or(() -> choferRepository.findByUsername(username).map(c -> c))
                    // Si no hay en ninguno, excepción
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        };
    }
}
