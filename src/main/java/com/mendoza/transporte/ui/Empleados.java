package com.mendoza.transporte.ui;

import com.mendoza.transporte.ui.empleados.EmpleadoService;
import com.mendoza.transporte.ui.empleados.EmpleadoResponse;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.grid.Grid;

@Route("index/empleados")
public class Empleados extends HorizontalLayout implements BeforeEnterObserver {

    private final VerticalLayout content = new VerticalLayout();
    private final EmpleadoService empleadoService = new EmpleadoService();
    private final Grid<EmpleadoResponse> grid = new Grid<>(EmpleadoResponse.class);

    public Empleados() {
        setSizeFull();
        Sidebar sidebar = new Sidebar();
        content.setPadding(true);

        String saludo = "                EMPLEADOS                ";
        content.add(new H1(saludo));

        configurarGrid();
        content.add(grid);
        add(sidebar, content);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        if (token == null || token.isEmpty()) {
            event.rerouteTo("index");
        } else {
            grid.setItems(empleadoService.obtenerTodos(token));
        }
    }

    private void configurarGrid() {
        grid.setColumns("id", "nombreCompleto");
        grid.getColumnByKey("id").setHeader("ID");
        grid.getColumnByKey("nombreCompleto").setHeader("Nombre completo");
    }
}
