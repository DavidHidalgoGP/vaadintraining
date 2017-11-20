package es.dhg.cursovaadinayesa.Ex001;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	private Navigator navigator;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		navigator= new Navigator(this, this);
		//Se declaran las vistas que van a ser usadas
		navigator.addView(PruebaView.NAME, new PruebaView(navigator));
		navigator.addView(ClienteView.NAME, new ClienteView(navigator));
		//Vista de inicio por defecto
		navigator.navigateTo(ClienteView.NAME);
		
	}

	/* Definicion del servlet */
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	/*
	 * Configuración del servlet - ui -> interfaz grafica que se usará para ese
	 * servlet - productionmode -> Comprimir ficheros o no
	 */
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
