package com.mendoza.transporte.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class Sidebar extends VerticalLayout {

    public Sidebar() {
        // Estilo general
        setWidth("220px");
        setHeightFull(); // para ocupar
        getStyle().set("background-color", "#ffffff");
        getStyle().set("border-right", "1px solid #ddd");
        setPadding(true);
        setSpacing(true);

        // Contenedor principal para los enlaces
        Div content = new Div();
        content.getStyle().set("display", "flex");
        content.getStyle().set("flex-direction", "column");
        content.getStyle().set("gap", "0.5em");
        content.getStyle().set("flex-grow", "1");

        Span title = new Span("Menú");
        title.getStyle().set("font-weight", "600");
        title.getStyle().set("font-size", "18px");
        title.getStyle().set("margin-bottom", "10px");
        content.add(title);

        String role = (String) VaadinSession.getCurrent().getAttribute("role");

        RouterLink inicio = new RouterLink("Inicio", MainView.class);
        styleLink(inicio);
        content.add(inicio);

        if ("CHOFER".equals(role)) {
            RouterLink adelantos = new RouterLink("Adelantos", Descuentos.class);
            styleLink(adelantos);
            content.add(adelantos);
        }

        if ("ADMIN".equals(role)) {
            RouterLink admin = new RouterLink("Administradores", Administradores.class);
            RouterLink choferes = new RouterLink("Choferes", Choferes.class);
            RouterLink empleados = new RouterLink("Empleados", Empleados.class);
            RouterLink descTotal = new RouterLink("Descuentos totales", DescuentoTotal.class);
            styleLink(admin, choferes, empleados, descTotal);
            content.add(admin, choferes, empleados, descTotal);
        }

        // Botón de logout
        Button logout = new Button("Cerrar sesión", e -> {
            VaadinSession.getCurrent().setAttribute("token", null);
            getUI().ifPresent(ui -> ui.navigate("index"));
        });
        logout.getStyle().set("margin-top", "auto");
        logout.getStyle().set("width", "100%");
        logout.getStyle().set("background-color", "#e74c3c");
        logout.getStyle().set("color", "white");
        logout.getStyle().set("border", "none");
        logout.getStyle().set("font-weight", "bold");

        // Añadir al layout principal
        add(content, logout);
        setFlexGrow(1, content); // Hace que el contenido crezca y empuje el botón hacia abajo
    }

    // Método para aplicar estilos a links
    private void styleLink(RouterLink... links) {
        for (RouterLink link : links) {
            link.getStyle().set("padding", "10px 14px");
            link.getStyle().set("border-radius", "6px");
            link.getStyle().set("text-decoration", "none");
            link.getStyle().set("color", "#1e1e1e"); // Texto oscuro
            link.getStyle().set("font-weight", "500");
            link.getStyle().set("background-color", "#f0f4f8"); // Fondo azul muy claro
            link.getStyle().set("margin-right", "8px");
            link.getStyle().set("transition", "background-color 0.2s, color 0.2s");
            link.getStyle().set("cursor", "pointer");

            // Efecto hover
            link.getElement().addEventListener("mouseenter", e -> {
                link.getStyle().set("background-color", "#d9e9f7"); // Azul claro
            });
            link.getElement().addEventListener("mouseleave", e -> {
                link.getStyle().set("background-color", "#f0f4f8"); // Volver al color base
            });
        }
    }

}
