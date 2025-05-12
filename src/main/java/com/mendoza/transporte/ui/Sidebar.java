package com.mendoza.transporte.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

public class Sidebar extends VerticalLayout {

    public Sidebar() {
        setWidth("200px");
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#f5f5f5");

        Span title = new Span("Menú");
        title.getStyle().set("font-weight", "bold");
        add(title);

        String role = (String) VaadinSession.getCurrent().getAttribute("role");

        //falta poner para diferenciar entre admin y chofer
        add(new RouterLink("Inicio", MainView.class));
        if ("CHOFER".equals(role)){
            add(new RouterLink("Adelantos", Descuentos.class));
        }
        if ("ADMIN".equals(role)){
            add(new RouterLink("Administradores", Administradores.class));
            add(new RouterLink("Choferes", Choferes.class));
            add(new RouterLink("Empleados", Empleados.class));
            add(new RouterLink("Descuentos totales", DescuentoTotal.class));
        }

        Button logout = new Button("Cerrar sesión", e -> {
            VaadinSession.getCurrent().setAttribute("token", null);
            getUI().ifPresent(ui -> ui.navigate("index"));
        });
        add(logout);
    }
}
