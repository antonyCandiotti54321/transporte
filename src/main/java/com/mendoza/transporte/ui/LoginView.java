package com.mendoza.transporte.ui;

import com.mendoza.transporte.ui.login.AuthService;
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

    private final LoginOverlay loginOverlay = new LoginOverlay();
    private final AuthService authService = new AuthService();

    public LoginView() {
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

        loginOverlay.addLoginListener(e -> {
            try {
                LoginRequest request = new LoginRequest(e.getUsername(), e.getPassword());
                AuthResponse response = authService.login(request);

                VaadinSession.getCurrent().setAttribute("token", response.getToken());
                VaadinSession.getCurrent().setAttribute("nombreCompleto", response.getNombreCompleto());
                VaadinSession.getCurrent().setAttribute("role", response.getRole().toString());
                
                loginOverlay.close();
                UI.getCurrent().getPage().setLocation("index/main");

            } catch (Exception ex) {
                ex.printStackTrace();
                loginOverlay.setError(true);
            }
        });

        add(loginOverlay);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        if (token != null && !token.isEmpty()) {
            event.rerouteTo("index/main");
        }
    }
}
