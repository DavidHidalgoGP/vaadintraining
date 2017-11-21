package es.ayesavaadin.Ex002.libro;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.GridContextClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import es.ayesavaadin.Ex002.cliente.Cliente;
import es.ayesavaadin.Ex002.cliente.ClienteService;
import es.ayesavaadin.Ex002.cliente.ClienteView;

@SuppressWarnings("serial")
public class LibrosPrestadosView extends HorizontalLayout implements View {
	private LibroService libroService = LibroService.getInstance();
	private ClienteService clienteservice = ClienteService.getInstance();

	private Grid<Libro> gridLibros = new Grid<>(Libro.class);
	private Grid<Cliente> gridClientes = new Grid<>(Cliente.class);
	final TextField filterTextLibros = new TextField();
	final TextField filterTextCliente = new TextField();
	private Libro libroPrestado;
	private Cliente cliente;
	private Navigator navigator;

	public static final String NAME = "libro";

	public LibrosPrestadosView(Navigator navigator) {
		this.navigator = navigator;
		// Decidimos el modo de seleción en la tabla (multiple, uno a uno, etc..)
		gridLibros.setSelectionMode(SelectionMode.SINGLE);
		// Decidimos que columnas mostrar, tienen que coincidir con los atributos de la
		// clase Cliente
		gridLibros.setColumns("isbn", "nombre", "autor", "cliente");
		// Definimos que la tabla ocupe todo el ancho maximo que pueda
		gridLibros.setSizeFull();

		// Decidimos el modo de seleción en la tabla (multiple, uno a uno, etc..)
		gridClientes.setSelectionMode(SelectionMode.SINGLE);
		// Decidimos que columnas mostrar, tienen que coincidir con los atributos de la
		// clase Cliente
		gridClientes.setColumns("nombre", "apellido", "email");
		// Definimos que la tabla ocupe todo el ancho maximo que pueda
		gridClientes.setSizeFull();

		actualizarTablaLibro();
		actualizarTablaCliente();

		Button borrarFiltroLibros = new Button(FontAwesome.TIMES);
		borrarFiltroLibros.setDescription("Borrar filtro");
		borrarFiltroLibros.addClickListener(e -> filterTextLibros.clear());
		filterTextLibros.setPlaceholder("Filtrar por nombre:");
		// Definimos el evento para cuando escribamos en el filtro se actualice la tabla
		filterTextLibros.addValueChangeListener(e -> actualizarTablaLibro());
		// ValueChangeMode.LAZY -> espera un tiempo antes de lanzar el evento
		filterTextLibros.setValueChangeMode(ValueChangeMode.LAZY);

		Button borrarFiltroCliente = new Button(FontAwesome.TIMES);
		borrarFiltroCliente.setDescription("Borrar filtro");
		borrarFiltroCliente.addClickListener(e -> filterTextCliente.clear());
		filterTextCliente.setPlaceholder("Filtrar por nombre:");
		// Definimos el evento para cuando escribamos en el filtro se actualice la tabla
		filterTextCliente.addValueChangeListener(e -> actualizarTablaCliente());
		// ValueChangeMode.LAZY -> espera un tiempo antes de lanzar el evento
		filterTextCliente.setValueChangeMode(ValueChangeMode.LAZY);

		// Definir layout especifico
		CssLayout filtradoLibros = new CssLayout();
		filtradoLibros.addComponent(filterTextLibros);
		filtradoLibros.addComponent(borrarFiltroLibros);

		CssLayout filtradoCliente = new CssLayout();
		filtradoCliente.addComponent(filterTextCliente);
		filtradoCliente.addComponent(borrarFiltroCliente);

		// Asignar estilo especifico al layout
		filtradoLibros.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filtradoCliente.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout botonera = new HorizontalLayout();
		Button prestar = new Button();
		prestar.setCaption("Prestar");
		prestar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		prestar.addClickListener(event -> {
			if (libroPrestado == null && cliente == null) {
				Notification.show("¡ADVERTENCIA!", "Debe seleccionar un libro y un cliente",
						Notification.Type.WARNING_MESSAGE);
			} else {
				if (libroPrestado == null) {
					Notification.show("¡ADVERTENCIA!", "Debe seleccionar un libro", Notification.Type.WARNING_MESSAGE);
				} else {
					if (cliente == null) {
						Notification.show("¡ADVERTENCIA!", "Debe seleccionar un cliente",
								Notification.Type.WARNING_MESSAGE);
					} else {
						if (libroPrestado != null && cliente != null) {
							libroService.prestar(gridClientes.asSingleSelect().getValue(),
									gridLibros.asSingleSelect().getValue());
							actualizarTablaLibro();
							gridClientes.deselectAll();
							libroPrestado=null;
							cliente=null;
						}
					}

				}
			}

		});
		gridLibros.asSingleSelect().addValueChangeListener(evento -> {
			if (gridLibros.asSingleSelect().getValue() != null) {
				this.libroPrestado = gridLibros.asSingleSelect().getValue();
			}
		});
		gridClientes.asSingleSelect().addValueChangeListener(evento -> {
			if (gridClientes.asSingleSelect().getValue() != null) {
				this.cliente = gridClientes.asSingleSelect().getValue();
			}
		});
		botonera.addComponents(filtradoLibros, prestar);
		VerticalLayout layoutLibros = new VerticalLayout();
		layoutLibros.addComponents(botonera, gridLibros);
		VerticalLayout layoutClientes = new VerticalLayout();
		layoutClientes.addComponents(filtradoCliente, gridClientes);
		this.addComponents(layoutLibros, layoutClientes);
		this.setSizeFull();
		this.setExpandRatio(layoutLibros, 2);
		this.setExpandRatio(layoutClientes, 1);
	}

	public void actualizarTablaLibro() {
		// Actualizamos los calores de la tabla con le valor que escribimos en el filtro
		gridLibros.setItems(libroService.findAll(filterTextLibros.getValue()));

	}

	public void actualizarTablaCliente() {
		// Actualizamos los calores de la tabla con le valor que escribimos en el filtro
		gridClientes.setItems(clienteservice.findAll(filterTextCliente.getValue()));
	}

}
