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
                const map = L.map('map').setView([-12.0464, -77.0428], 13); // Lima, Perú

                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '© OpenStreetMap contributors'
                }).addTo(map);

                // Marcador de camión
                L.marker([-12.0464, -77.0428])
                    .addTo(map)
                    .bindPopup('Camión A')
                    .openPopup();

                // Ruta entre dos puntos
                L.Routing.control({
                    waypoints: [
                        L.latLng(-12.0464, -77.0428),
                        L.latLng(-12.0560, -77.0500)
                    ],
                    createMarker: function () { return null; }
                }).addTo(map);
            });
        """);
    }
}
