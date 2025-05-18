package com.mendoza.transporte.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("mapa")
public class MapaView extends VerticalLayout {

    public MapaView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);  // Centra horizontalmente
        setJustifyContentMode(JustifyContentMode.CENTER);  // Centra verticalmente

        Div mapaDiv = new Div();
        mapaDiv.setId("map");
        mapaDiv.setWidth("500px");     // Ancho reducido
        mapaDiv.setHeight("400px");    // Alto reducido
        mapaDiv.getStyle().set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)"); // Opcional: sombra
        add(mapaDiv);

        // Carga los scripts de Leaflet
        UI.getCurrent().getPage().addStyleSheet("https://unpkg.com/leaflet/dist/leaflet.css");
        UI.getCurrent().getPage().addJavaScript("https://unpkg.com/leaflet/dist/leaflet.js");
        UI.getCurrent().getPage().addJavaScript("https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.min.js");

        UI.getCurrent().getPage().executeJs("""
            setTimeout(() => {
                var map = L.map('map').setView([-12.0464, -77.0428], 13); // Lima, Perú

                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '© OpenStreetMap contributors'
                }).addTo(map);

                // Marcador de camión
                var marker = L.marker([-12.0464, -77.0428]).addTo(map)
                    .bindPopup('Camión A')
                    .openPopup();

                // Ruta entre dos puntos
                L.Routing.control({
                    waypoints: [
                        L.latLng(-12.0464, -77.0428),
                        L.latLng(-12.0560, -77.0500)
                    ],
                    createMarker: () => null
                }).addTo(map);
            }, 1000);
        """);
    }
}
