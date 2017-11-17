package es.dhg.cursovaadinayesa.Ex001;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;

@SuppressWarnings("serial")
public class ClienteFormBack extends ClienteForm {
	private ClienteService clienteService=ClienteService.getInstance();
	//Nos ayudara a asociar los atributos del objeto cliente con los campos del formulario
	private Binder <Cliente> binder = new Binder<>(Cliente.class);
	private Cliente cliente;
	private MyUI myUi;
	
	public ClienteFormBack(MyUI ui) {
		//Asignat atajo de teclado al boton guardar
		botonGuardar.setClickShortcut(KeyCode.ENTER);
		
		estados.setItems(ClienteEstado.values());
		//Asociar binder con este formulario
		binder.bindInstanceFields(this);
		
		botonGuardar.addClickListener(e ->this.guardar());
		botonEliminar.addClickListener(e->this.borrar());
	}

	private void borrar() {
		// TODO Auto-generated method stub
		clienteService.borrar(cliente);
		myUi.actualizarTabla();
		setVisible(false);
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
		binder.setBean(cliente);
		
		botonEliminar.setVisible(cliente.getId()!=null);
		setVisible(true);
		nombre.selectAll();
	}

	private void guardar() {
		// TODO Auto-generated method stub
		clienteService.guardar(cliente);
		myUi.actualizarTabla();
		setVisible(false);
	}
}
