package com.mendoza.transporte.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

@CssImport("./styles/shared-styles..css")
public class Sidebar extends VerticalLayout {

    public Sidebar() {
        setWidth("220px");
        setPadding(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("background-color", "#2c3e50");
        getStyle().set("color", "#ecf0f1");
        getStyle().set("min-height", "100vh");
        getStyle().set("padding", "20px");

        H1 title = new H1("Menú");
        title.getStyle()
                .set("color", "#ecf0f1")
                .set("font-size", "24px")
                .set("margin-bottom", "20px");
        add(title);

        String role = (String) VaadinSession.getCurrent().getAttribute("role");

        add(createNavLink("Inicio", VaadinIcon.HOME, MainView.class));

        if ("CHOFER".equals(role)) {
            add(createNavLink("Adelantos", VaadinIcon.MONEY, Descuentos.class));
        }

        if ("ADMIN".equals(role)) {
            add(createNavLink("Administradores", VaadinIcon.USER, Administradores.class));
            add(createNavLink("Choferes", VaadinIcon.USERS, Choferes.class));
            add(createNavLink("Empleados", VaadinIcon.CLIPBOARD_USER, Empleados.class));
            add(createNavLink("Descuentos totales", VaadinIcon.CASH, DescuentoTotal.class));
        }

        Button logout = new Button("Cerrar sesión", new Icon(VaadinIcon.SIGN_OUT));
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        logout.getStyle().set("margin-top", "auto"); // lo empuja al final si usas full-height
        logout.addClickListener(e -> {
            VaadinSession.getCurrent().setAttribute("token", null);
            getUI().ifPresent(ui -> ui.navigate("index"));
        });
        add(logout);
    }

    private RouterLink createNavLink(String text, VaadinIcon icon, Class<?> navigationTarget) {
        Icon iconComponent = icon.create();
        iconComponent.getStyle().set("margin-right", "8px").set("color", "#ecf0f1");

        RouterLink link = new RouterLink(null, navigationTarget);
        link.add(iconComponent, new com.vaadin.flow.component.html.Span(text));
        link.getStyle()
                .set("color", "#ecf0f1")
                .set("text-decoration", "none")
                .set("margin-bottom", "10px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("font-size", "16px");
        return link;
    }
}
