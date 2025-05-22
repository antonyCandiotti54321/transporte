package com.mendoza.transporte.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@Route("mapa")
public class MapaView extends HorizontalLayout {

    public MapaView() {
        setSizeFull(); // ocupa todo el espacio
        setSpacing(true); // espacio entre los hijos
        setPadding(true); // algo de margen interno

        Sidebar sidebar = new Sidebar();
        Div mapaDiv = new Div();
        mapaDiv.setId("map");
        mapaDiv.setHeight("100%"); // ocupa toda la altura
        mapaDiv.setWidthFull();    // permite crecer
        mapaDiv.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");
        mapaDiv.getStyle().set("margin-left", "10px"); // opcional, más separación

        add(sidebar, mapaDiv);
        setFlexGrow(1, mapaDiv); // el mapa se expande

        UI ui = UI.getCurrent();
        ui.getPage().addStyleSheet("https://unpkg.com/leaflet/dist/leaflet.css");
        ui.getPage().addJavaScript("https://unpkg.com/leaflet/dist/leaflet.js");
        ui.getPage().addJavaScript("https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.min.js");
        ui.getPage().addJavaScript("https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js");
        ui.getPage().addJavaScript("https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js");

        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        System.out.println("🔐 Token JWT en la sesión actual: " + token);

        ui.getPage().executeJs("""
            const token = $0;
            const map = L.map('map').setView([-12.0464, -77.0428], 19);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors'
            }).addTo(map);

            let markers = {}; // Diccionario de marcadores por ID de chofer

            const socket = new SockJS('https://transporte-ecug.onrender.com/ws?token=' + encodeURIComponent(token));
            const stompClient = Stomp.over(socket);

            stompClient.connect({}, function(frame) {
                console.log('📥 Conectado al WebSocket:', frame);
                stompClient.subscribe('/topic/ubicacion', function(mensaje) {
                    const data = JSON.parse(mensaje.body);
                    const lat = data.latitud;
                    const lng = data.longitud;
                    const id = data.id;

                    if (markers[id]) {
                        // Actualiza la ubicación del marcador existente
                        markers[id].setLatLng([lat, lng]);
                    } else {
                        // Crea un nuevo marcador para este ID
                        const newMarker = L.marker([lat, lng])
                            .addTo(map)
                            .bindPopup('Camión ' + id)
                            .openPopup();
                        markers[id] = newMarker;
                    }
                });
            }, function(error) {
                console.error('Error al conectar WebSocket:', error);
            });
        """, token);
    }
}
