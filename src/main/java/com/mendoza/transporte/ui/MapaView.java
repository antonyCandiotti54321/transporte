package com.mendoza.transporte.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("mapa")
public class MapaView extends VerticalLayout {

    public MapaView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);  // Centrar horizontalmente
        setJustifyContentMode(JustifyContentMode.CENTER);  // Centrar verticalmente

        // Contenedor del mapa
        Div mapaDiv = new Div();
        mapaDiv.setId("map");
        mapaDiv.setWidth("500px");
        mapaDiv.setHeight("400px");
        mapaDiv.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");
        add(mapaDiv);

        // Cargar los estilos y scripts necesarios de Leaflet y Leaflet Routing Machine
        UI ui = UI.getCurrent();
        ui.getPage().addStyleSheet("https://unpkg.com/leaflet/dist/leaflet.css");
        ui.getPage().addJavaScript("https://unpkg.com/leaflet/dist/leaflet.js");
        ui.getPage().addJavaScript("https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.min.js");

        // Ejecutar JavaScript para inicializar el mapa
        ui.getPage().executeJs("""
    window.addEventListener('load', function () {
        const map = L.map('map').setView([-12.0464, -77.0428], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors'
        }).addTo(map);

        let marker = null;

        // WebSocket y STOMP
        const socket = new SockJS('https://transporte-ecug.onrender.com/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('📥 Conectado al WebSocket:', frame);

            stompClient.subscribe('/topic/ubicacion', function (mensaje) {
                const data = JSON.parse(mensaje.body);
                console.log('📍 Ubicación recibida:', data);

                const lat = data.latitud;
                const lng = data.longitud;

                if (marker) {
                    marker.setLatLng([lat, lng]);
                } else {
                    marker = L.marker([lat, lng]).addTo(map).bindPopup('Camión ' + data.id).openPopup();
                }

                map.setView([lat, lng], 13);
            });
        });
    });
""");

    }
}
