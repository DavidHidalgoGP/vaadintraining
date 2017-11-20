package es.dhg.cursovaadinayesa.Ex001;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ClienteView extends VerticalLayout implements View {
	private ClienteService clienteService = ClienteService.getInstance();
	private Grid<Cliente> grid = new Grid<>(Cliente.class);
	final TextField filterText = new TextField();
	private ClienteFormBack form = new ClienteFormBack(this);
	private Navigator navigator;
	//Nombre de la vista, que nos ayudara luego a la hoa de "navegar" entre vistas
	public static final String NAME="cliente";

	public ClienteView(Navigator navigator) {
		this.navigator=navigator;
		
		form.setVisible(false);
		// Decidimos el modo de seleción en la tabla (multiple, uno a uno, etc..)
		grid.setSelectionMode(SelectionMode.SINGLE);
		// Decidimos que columnas mostrar, tienen que coincidir con los atributos de la
		// clase Cliente
		grid.setColumns("nombre", "apellido", "email");
		// Definimos que la tabla ocupe todo el ancho maximo que pueda
		grid.setSizeFull();

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
		// Creamos un layout horizontal para la tabla y el form de contacto
		HorizontalLayout main = new HorizontalLayout();
		main.addComponents(grid, form);
		main.setSizeFull();
		form.setSizeFull();
		// Dos partes de la pantalla para el grid
		main.setExpandRatio(grid, 2);
		// Una parte de la pantalla para el el formulario
		main.setExpandRatio(form, 1);
		// Evento cuando se seleccione alguna fila de la tabla
		grid.asSingleSelect().addValueChangeListener(evento -> {
			if (evento.getValue() == null) {
				form.setVisible(false);
			} else {
				form.setCliente(evento.getValue());
			}
		});
		HorizontalLayout botonera = new HorizontalLayout();
		Button nuevoCliente = new Button();
		nuevoCliente.setCaption("Nuevo Cliente");
		nuevoCliente.setStyleName(ValoTheme.BUTTON_PRIMARY);
		nuevoCliente.addClickListener(evento -> {
			grid.asSingleSelect().clear();
			form.setCliente(new Cliente());
		});
		
		Button navegar = new Button("Prueba");
		navegar.addClickListener(event -> this.navigator.navigateTo(PruebaView.NAME));
		navegar.setCaption("Navegar a PruebaView");
		botonera.addComponents(filtrado, nuevoCliente,navegar);
		this.addComponents(botonera, main);
	}
	
	public void actualizarTabla() {
		// Actualizamos los calores de la tabla con le valor que escribimos en el filtro
		grid.setItems(clienteService.findAll(filterText.getValue()));
	}
}
