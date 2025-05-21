package com.mendoza.transporte.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("mapa")
public class MapaView extends VerticalLayout {

    public MapaView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Div mapaDiv = new Div();
        mapaDiv.setId("map");
        mapaDiv.setWidth("600px");
        mapaDiv.setHeight("450px");
        mapaDiv.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");
        add(mapaDiv);

        UI ui = UI.getCurrent();

        // Carga CSS y JS necesarios (Leaflet, SockJS, STOMP)
        ui.getPage().addStyleSheet("https://unpkg.com/leaflet/dist/leaflet.css");
        ui.getPage().addJavaScript("https://unpkg.com/leaflet/dist/leaflet.js");
        ui.getPage().addJavaScript("https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js");
        ui.getPage().addJavaScript("https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js");

        // Ejecuta JS para inicializar mapa y conectar WebSocket
        ui.getPage().executeJs("""
            // Crear el mapa centrado en Lima
            const map = L.map('map').setView([-12.0464, -77.0428], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors'
            }).addTo(map);

            // Mapa para guardar marcadores por ID (camión)
            const markers = new Map();

            // Configurar conexión WebSocket con SockJS + STOMP
            const socket = new SockJS('https://transporte-ecug.onrender.com/ws');
            const stompClient = Stomp.over(socket);

            stompClient.connect({}, function(frame) {
                console.log('Conectado a WebSocket:', frame);

                stompClient.subscribe('/topic/ubicacion', function(mensaje) {
                    const data = JSON.parse(mensaje.body);
                    console.log('Ubicación recibida:', data);

                    const id = data.id;
                    const lat = data.latitud;
                    const lng = data.longitud;

                    if(markers.has(id)) {
                        // Actualizar posición del marcador existente
                        markers.get(id).setLatLng([lat, lng]);
                    } else {
                        // Crear nuevo marcador y guardarlo
                        const marker = L.marker([lat, lng])
                            .addTo(map)
                            .bindPopup('Camión ' + id);
                        markers.set(id, marker);
                    }

                    // Opcional: centrar mapa en la última ubicación recibida
                    map.setView([lat, lng], 13);
                });
            }, function(error) {
                console.error('Error al conectar WebSocket:', error);
            });
        """);
    }
}
