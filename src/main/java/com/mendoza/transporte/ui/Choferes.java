package com.mendoza.transporte.ui;
import com.mendoza.transporte.ui.choferes.ChoferService;
import com.mendoza.transporte.ui.choferes.ChoferResponse;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.grid.Grid;

@Route("index/choferes")
public class Choferes extends HorizontalLayout implements BeforeEnterObserver {

    private final VerticalLayout content = new VerticalLayout();
    private final ChoferService choferService = new ChoferService();
    private final Grid<ChoferResponse> grid = new Grid<>(ChoferResponse.class);

    public Choferes() {
        setSizeFull();
        Sidebar sidebar = new Sidebar();
        content.setPadding(true);

        String saludo = "                CHOFERES                ";
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
            grid.setItems(choferService.obtenerTodos(token));
        }
    }

    private void configurarGrid() {
        grid.setColumns("id", "username", "nombreCompleto");
        grid.getColumnByKey("id").setHeader("ID");
        grid.getColumnByKey("username").setHeader("Usuario");
        grid.getColumnByKey("nombreCompleto").setHeader("Nombre completo");
    }
}
