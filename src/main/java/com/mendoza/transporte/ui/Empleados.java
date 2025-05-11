package com.mendoza.transporte.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("index/empleados")
public class Empleados extends HorizontalLayout implements BeforeEnterObserver {

    private final VerticalLayout content = new VerticalLayout();

    public Empleados(){
        setSizeFull();

        Sidebar sidebar = new Sidebar(); // crear sidebar aquí

        content.setPadding(true);

        String nombre = (String) VaadinSession.getCurrent().getAttribute("nombreCompleto");

        String saludo = "Hola";
        if (nombre != null && !nombre.isEmpty()) {
            saludo += ", tu nombre es " + nombre;
        }
        saludo += ". Esta es la vista principal";
        content.add(new H1(saludo));
        System.out.println("*****************************************");
        System.out.println(nombre);
        System.out.println("*****************************************");

        add(sidebar, content); // usar después de declarar
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        if (token == null || token.isEmpty()) {
            event.rerouteTo("index");
        }
    }
}
