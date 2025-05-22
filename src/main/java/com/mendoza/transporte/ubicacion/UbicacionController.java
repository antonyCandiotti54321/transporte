package com.mendoza.transporte.ubicacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class UbicacionController {

    @MessageMapping("/ubicacion")  // Recibe mensajes en /app/ubicacion
    @SendTo("/topic/ubicacion")    // Reenvía a todos suscritos en /topic/ubicacion
    public UbicacionDTO recibirUbicacion(@Valid UbicacionDTO ubicacion) throws Exception {
        // Aquí puedes guardar en BD o procesar
        System.out.println("Ubicación recibida: " + ubicacion.getId() + ", " + ubicacion.getLatitud() + ", " + ubicacion.getLongitud());
        return ubicacion; // Esto se envía a todos los clientes conectados
    }
}
