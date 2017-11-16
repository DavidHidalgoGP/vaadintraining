package es.dhg.cursovaadinayesa.Ex001;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
	private ClienteService clienteService = ClienteService.getInstance();
	private Grid<Cliente> grid = new Grid<>(Cliente.class);
	final TextField filterText = new TextField();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		// Decidimos el modo de seleción en la tabla (multiple, uno a uno, etc..)
		grid.setSelectionMode(SelectionMode.SINGLE);
		// Decidimos que columnas mostrar, tienen que coincidir con los atributos de la
		// clase Cliente
		grid.setColumns("nombre", "apellido", "email");

		actualizarTabla();

		Button borrarFiltro = new Button(FontAwesome.TIMES);
		borrarFiltro.setDescription("Borrar filtro");
		borrarFiltro.addClickListener(e -> filterText.clear());
		filterText.setPlaceholder("Filtrar por nombre:");
		// Definimos el evento para cuando escribamos en el filtro se actualice la tabla
		filterText.addValueChangeListener(e -> actualizarTabla());
		// ValueChangeMode.LAZY -> espera un tiempo antes de lanzar el evento
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		// Definir layout especifico
		CssLayout filtrado = new CssLayout();
		filtrado.addComponent(filterText);
		filtrado.addComponent(borrarFiltro);
		// Asignar estilo especifico al layout
		filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		layout.addComponents(filtrado, grid);
		
		setContent(layout);
	}

	private void actualizarTabla() {
		// Actualizamos los calores de la tabla con le valor que escribimos en el filtro
		grid.setItems(clienteService.findAll(filterText.getValue()));
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
