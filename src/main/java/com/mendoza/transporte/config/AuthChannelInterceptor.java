package com.mendoza.transporte.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if ("CONNECT".equals(accessor.getCommand().name())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null || !isValidToken(token)) {
                throw new IllegalArgumentException("Token inválido");
            }

            // Puedes guardar al usuario en el accessor si lo necesitas
            // accessor.setUser(new UsernamePasswordAuthenticationToken(...));
        }

        return message;
    }

    private boolean isValidToken(String token) {
        // Aquí validas el token JWT, por ejemplo con tu servicio de tokens
        return token.startsWith("Bearer ") && validateJwt(token.substring(7));
    }

    private boolean validateJwt(String jwt) {
        // Aquí va la lógica real de validación JWT
        return true;
    }
}
