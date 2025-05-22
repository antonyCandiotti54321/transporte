package com.mendoza.transporte.ubicacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WebSocket - Ubicación", description = "Canal de ubicación en tiempo real")
@Controller
public class UbicacionController {

    @Operation(
            summary = "Recibir ubicación",
            description = """
            Endpoint WebSocket para recibir ubicación en tiempo real.

            • Enviar a: `/app/ubicacion`  
            • Recibir de: `/topic/ubicacion`  
            • Payload esperado: UbicacionDTO
        """
    )
    @MessageMapping("/ubicacion")  // Recibe mensajes en /app/ubicacion
    @SendTo("/topic/ubicacion")    // Reenvía a todos suscritos en /topic/ubicacion
    public UbicacionDTO recibirUbicacion(@Valid UbicacionDTO ubicacion) throws Exception {
        // Aquí puedes guardar en BD o procesar
        System.out.println("Ubicación recibida: " + ubicacion.getId() + ", " + ubicacion.getLatitud() + ", " + ubicacion.getLongitud());
        return ubicacion; // Esto se envía a todos los clientes conectados
    }
}
