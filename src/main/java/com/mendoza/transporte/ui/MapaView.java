package com.mendoza.transporte.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("mapa")
public class MapaView extends VerticalLayout {

    public MapaView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Div mapaDiv = new Div();
        mapaDiv.setId("map");
        mapaDiv.setWidth("600px");      // aumentado 20%
        mapaDiv.setHeight("480px");     // aumentado 20%
        mapaDiv.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");
        add(mapaDiv);

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

    let marker = null;

    const socket = new SockJS('https://transporte-ecug.onrender.com/ws?token=' + encodeURIComponent(token));
    const stompClient = Stomp.over(socket);

    stompClient.connect(
      {},
      function(frame) {
        console.log('📥 Conectado al WebSocket:', frame);

        stompClient.subscribe('/topic/ubicacion', function(mensaje) {
            const data = JSON.parse(mensaje.body);
            console.log('📍 Ubicación recibida:', data);

            const lat = data.latitud;
            const lng = data.longitud;

            if (marker) {
                marker.setLatLng([lat, lng]);
            } else {
                marker = L.marker([lat, lng]).addTo(map).bindPopup('Camión ' + data.id).openPopup();
            }

            // No mover la cámara con map.setView para respetar la interacción del usuario
        });
      },
      function(error) {
        console.error('Error al conectar WebSocket:', error);
      }
    );
""", token);


    }
}
