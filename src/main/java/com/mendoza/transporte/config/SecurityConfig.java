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
                .cors(AbstractHttpConfigurer::disable)  // si usas Spring Security 6 puedes hacer .cors(withDefaults())
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

    /**
     * Bean que define la política de CORS:
     * - acepta cualquier origen ("*")
     * - acepta todos los métodos HTTP
     * - acepta todas las cabeceras
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));              // permite todas las URLs
        config.setAllowedMethods(List.of("*"));              // GET, POST, PUT, DELETE, etc.
        config.setAllowedHeaders(List.of("*"));              // Authorization, Content-Type, etc.
        config.setAllowCredentials(true);                    // si quieres enviar cookies o auth

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // aplica esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
