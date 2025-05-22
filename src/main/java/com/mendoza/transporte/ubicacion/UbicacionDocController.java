package com.mendoza.transporte.ubicacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocket-docs")
@Tag(name = "WebSocket - Ubicación", description = "Canal de ubicación en tiempo real")
public class UbicacionDocController {  // ← le cambiamos el nombre

    @Operation(
            summary = "Documentación del WebSocket",
            description = """
            WebSocket para transmitir ubicación en tiempo real.

            • Enviar a: `/app/ubicacion`  
            • Recibir de: `/topic/ubicacion`  
            • Payload: UbicacionDTO
        """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ejemplo de UbicacionDTO",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UbicacionDTO.class),
                                    examples = @ExampleObject(
                                            name = "Ejemplo de UbicacionDTO",
                                            value = """
                            {
                              "id": 123,
                              "latitud": -12.0464,
                              "longitud": -77.0428
                            }
                        """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/ubicacion")
    public ResponseEntity<String> doc() {
        return ResponseEntity.ok("Este endpoint solo existe para documentar el WebSocket.");
    }
}
