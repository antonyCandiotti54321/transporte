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


@Configuration //clase de configuracion, va a tener metodos que van a estar anotados con anotation bean, configurar y crear objetos que requerimos en nuestra aplicacion
@EnableWebSecurity
@RequiredArgsConstructor //Crea un constructor que incluye solo los campos final y los que tienen la anotación @NonNull
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("api/auth/**").permitAll()
                                .requestMatchers("api/health/**").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Solo técnicos
                                .requestMatchers("/api/chofer/**").hasRole("CHOFER") // Solo clientes
                                .anyRequest().authenticated()
                )

                // .formLogin(withDefaults())       Security spring
                .sessionManagement(sessionManager->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
