package es.ayesavaadin.Ex002.libro;

import java.io.Serializable;
import java.time.LocalDate;

import es.ayesavaadin.Ex002.cliente.Cliente;

/* Las clases java para Vaadin deben ser serializables */
@SuppressWarnings("serial")
public class Libro implements Serializable, Cloneable {
	private String nombre;
	private String isbn;
	private String autor;
	private Cliente cliente;

	public Libro(String nombre, String isbn, String autor, Cliente cliente) {
		super();
		this.nombre = nombre;
		this.isbn = isbn;
		this.autor = autor;
		this.cliente = cliente;
	}

	@Override
	public Libro clone() throws CloneNotSupportedException {
		return (Libro) super.clone();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hash = 5;
		hash = 43 * hash + (this.isbn == null ? 0 : isbn.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.isbn == null) {
			return false;
		}
		if (obj instanceof Libro && obj.getClass().equals(getClass())) {
			return this.isbn.equals(((Libro) obj).isbn);
		}
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nombre: "+nombre+" / ISBN: "+isbn;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
