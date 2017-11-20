package es.ayesavaadin.Ex002.libro;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.GridContextClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import es.ayesavaadin.Ex002.cliente.Cliente;
import es.ayesavaadin.Ex002.cliente.ClienteService;
import es.ayesavaadin.Ex002.cliente.ClienteView;

@SuppressWarnings("serial")
public class LibrosPrestadosView extends VerticalLayout implements View {
	private LibroService libroService = LibroService.getInstance();
	private ClienteService clienteservice = ClienteService.getInstance();

	private Grid<Libro> grid = new Grid<>(Libro.class);
	private Grid<Cliente> gridClientes = new Grid<>(Cliente.class);
	final TextField filterText = new TextField();
	final TextField filterTextCliente = new TextField();
	private Navigator navigator;

	public static final String NAME = "libro";

	public LibrosPrestadosView(Navigator navigator) {
		this.navigator = navigator;
		// Decidimos el modo de seleción en la tabla (multiple, uno a uno, etc..)
		grid.setSelectionMode(SelectionMode.SINGLE);
		// Decidimos que columnas mostrar, tienen que coincidir con los atributos de la
		// clase Cliente
		grid.setColumns("isbn", "nombre", "autor", "cliente");
		// Definimos que la tabla ocupe todo el ancho maximo que pueda
		grid.setSizeFull();

		// Decidimos el modo de seleción en la tabla (multiple, uno a uno, etc..)
		gridClientes.setSelectionMode(SelectionMode.SINGLE);
		// Decidimos que columnas mostrar, tienen que coincidir con los atributos de la
		// clase Cliente
		gridClientes.setColumns("nombre", "apellido", "email");
		// Definimos que la tabla ocupe todo el ancho maximo que pueda
		gridClientes.setSizeFull();

		actualizarTablaLibro();
		actualizarTablaCliente();

		Button borrarFiltro = new Button(FontAwesome.TIMES);
		borrarFiltro.setDescription("Borrar filtro");
		borrarFiltro.addClickListener(e -> filterText.clear());
		filterText.setPlaceholder("Filtrar por nombre:");
		// Definimos el evento para cuando escribamos en el filtro se actualice la tabla
		filterText.addValueChangeListener(e -> actualizarTablaLibro());
		// ValueChangeMode.LAZY -> espera un tiempo antes de lanzar el evento
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		Button borrarFiltroCliente = new Button(FontAwesome.TIMES);
		borrarFiltroCliente.setDescription("Borrar filtro");
		borrarFiltroCliente.addClickListener(e -> filterTextCliente.clear());
		filterTextCliente.setPlaceholder("Filtrar por nombre:");
		// Definimos el evento para cuando escribamos en el filtro se actualice la tabla
		filterTextCliente.addValueChangeListener(e -> actualizarTablaLibro());
		// ValueChangeMode.LAZY -> espera un tiempo antes de lanzar el evento
		filterTextCliente.setValueChangeMode(ValueChangeMode.LAZY);

		// Definir layout especifico
		CssLayout filtrado = new CssLayout();
		filtrado.addComponent(filterText);
		filtrado.addComponent(borrarFiltro);
		CssLayout filtradoCliente = new CssLayout();
		filtradoCliente.addComponent(filterTextCliente);
		filtradoCliente.addComponent(borrarFiltroCliente);
		// Asignar estilo especifico al layout
		filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filtradoCliente.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		HorizontalLayout botonera = new HorizontalLayout();
		Button prestar = new Button();
		prestar.setCaption("Prestar");
		prestar.setVisible(false);
		prestar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		prestar.addClickListener(event -> this.navigator.navigateTo(ClienteView.NAME));
		grid.asSingleSelect().addValueChangeListener(evento -> {
			prestar.setVisible(true);
		});
		botonera.addComponents(filtrado, prestar);
		VerticalLayout layoutLibros = new VerticalLayout();
		layoutLibros.addComponents(botonera, grid);
		VerticalLayout layoutClientes = new VerticalLayout();
		layoutLibros.addComponents(filtradoCliente, gridClientes);
		HorizontalLayout listados = new HorizontalLayout();
		listados.addComponents(layoutLibros, layoutClientes);
		this.setExpandRatio(layoutClientes, 1);
		this.setExpandRatio(layoutLibros, 1);

		this.addComponents(botonera, grid);
	}

	public void actualizarTablaLibro() {
		// Actualizamos los calores de la tabla con le valor que escribimos en el filtro
		grid.setItems(libroService.findAll(filterText.getValue()));

	}

	public void actualizarTablaCliente() {
		// Actualizamos los calores de la tabla con le valor que escribimos en el filtro
		gridClientes.setItems(clienteservice.findAll(filterTextCliente.getValue()));		
	}

}
