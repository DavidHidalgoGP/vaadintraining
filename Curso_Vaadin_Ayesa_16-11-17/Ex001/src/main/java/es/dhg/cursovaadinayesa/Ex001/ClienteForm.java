package es.dhg.cursovaadinayesa.Ex001;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class ClienteForm extends FormLayout {
	protected TextField nombre;
	protected TextField apellido;
	protected DateField fechaNacimiento;
	protected NativeSelect<es.dhg.cursovaadinayesa.Ex001.ClienteEstado> estados;
	protected Button botonGuardar;
	protected Button botonEliminar;

	public ClienteForm() {
		Design.read(this);
	}
}
