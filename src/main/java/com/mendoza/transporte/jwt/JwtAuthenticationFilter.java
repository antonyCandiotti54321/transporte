package com.mendoza.transporte.jwt;

import com.mendoza.transporte.administradores.AdministradorRepository;
import com.mendoza.transporte.choferes.ChoferRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders; //HAY OTRO IGUAL, NO FUNCIONAL DE LA MISMA MANERA, CUIDADO
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils; // hAY OTRO IGUAL
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component                                      //el filtro lo ejecutará una vez por cada solicitud http
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Para autorizar cuando te envian los tokens que han recibido al momento de iniciar secion, verificar token
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ChoferRepository choferRepository;
    private final AdministradorRepository administradorRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String username = jwtService.getUsernameFromToken(token);
        final String userType = jwtService.getUserTypeFromToken(token); // ← Se obtiene el tipo de usuario

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = null;

            // Determina qué tipo de usuario es y lo busca en su repositorio
            if ("ADMINISTRADOR".equalsIgnoreCase(userType)) {
                userDetails = administradorRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Administrador no encontrado"));
            } else if ("CHOFER".equalsIgnoreCase(userType)) {
                userDetails = choferRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Chofer no encontrado"));
            }

            // Validar token y setear autenticación
            if (userDetails != null && jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Extraer token del header
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
