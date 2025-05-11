package com.mendoza.transporte.ui;

import com.mendoza.transporte.auth.AuthService;
import com.mendoza.transporte.auth.LoginRequest;
import com.mendoza.transporte.auth.AuthResponse;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route("index")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthService authService;
    private final LoginOverlay loginOverlay = new LoginOverlay();

    @Autowired
    public LoginView(AuthService authService) {
        this.authService = authService;
        setSizeFull();

        // Configurar textos en español
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Iniciar Sesión");
        i18n.getForm().setUsername("Usuario");
        i18n.getForm().setPassword("Contraseña");
        i18n.getForm().setSubmit("Entrar");
        i18n.getErrorMessage().setTitle("Credenciales inválidas");
        i18n.getErrorMessage().setMessage("Revisa usuario y contraseña");
        loginOverlay.setI18n(i18n);

        loginOverlay.setAction("/index");
        loginOverlay.setOpened(true);

        // Evento al hacer login
        loginOverlay.addLoginListener(e -> {
            try {
                LoginRequest request = new LoginRequest(e.getUsername(), e.getPassword());
                AuthResponse resp = authService.login(request);

                // Guardar token en sesión de Vaadin
                // Guardar token y nombre en sesión de Vaadin
                VaadinSession.getCurrent().setAttribute("token", resp.getToken());
                VaadinSession.getCurrent().setAttribute("nombreCompleto", resp.getNombreCompleto());


                loginOverlay.close();

                // Redirigir con recarga completa y actualización de URL
                UI.getCurrent().getPage().setLocation("index/main");

            } catch (Exception ex) {
                loginOverlay.setError(true);
            }
        });

        add(loginOverlay);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        if (token != null && !token.isEmpty()) {
            event.rerouteTo("index/main"); // o LoginView.class, pero usando la ruta adecuada
        }
    }

}

