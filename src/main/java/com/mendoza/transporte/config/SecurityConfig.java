package com.mendoza.transporte.config;

import com.mendoza.transporte.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) habilita CORS usando la configuración que definiremos más abajo
                .cors(withDefaults()) // si usas Spring Security 6 puedes hacer .cors(withDefaults())
                // 2) desactiva CSRF (no necesario para APIs REST sin sesión)
                .csrf(AbstractHttpConfigurer::disable)
                // 3) reglas de acceso
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/health/**").permitAll()
                        .requestMatchers("/ping/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/chofer/**").hasRole("CHOFER")
                        .anyRequest().authenticated()
                )
                // 4) sin sesión (stateless JWT)
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Solo permite peticiones desde tu frontend en desarrollo
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        config.setAllowedMethods(List.of("*"));
        // Permite todas las cabeceras que el cliente envíe
        config.setAllowedHeaders(List.of("*"));
        // Si necesitas enviar cookies o Authorization headers (por ejemplo JWT), déjalo en true
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuración a todas las rutas de la API
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
