package es.ayesavaadin.Ex002.libro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LibroService {
	private static LibroService instancia;
	private final Map<String, Libro> libros = new HashMap<>();

	private LibroService() {
	};

	public static LibroService getInstance() {
		if (instancia == null) {
			instancia = new LibroService();
			instancia.generarDatos();
		}
		return instancia;
	}

	private void generarDatos() {
		// TODO Auto-generated method stub
		if (findAll().isEmpty()) {
			guardar(new Libro("El fuego invisible", "9788408178941", "Javier Sierra", null));
			guardar(new Libro("Origen", " 9788408177081", "Dan Brown", null));
			guardar(new Libro("Patria", "9788490663196", "Fernando Aranburu", null));
			guardar(new Libro("Indomable", "9788490438800", "SRTBEBI", null));
		}
	}

	public List<Libro> findAll() {
		// TODO Auto-generated method stub
		return findAll(null);
	}

	public List<Libro> findAll(String cadena) {
		return libros.values().stream().filter(libro -> {
			return (cadena == null || cadena.isEmpty())
					|| (libro.getNombre().toLowerCase().contains(cadena.toLowerCase()));
		}).collect(Collectors.toList());

	}

	public long count() {
		return libros.size();
	}

	public void borrar(Libro cliente) {
		libros.remove(cliente.getIsbn());
	}

	public void guardar(Libro libro) {
		// TODO Auto-generated method stub
		if (libro == null) {
			return;
		} else {
			if (libro.getIsbn() == null) {
				libro.setIsbn("NO DEFINIDO");
				;
			}
		}
		try {
			libro = libro.clone();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		libros.put(libro.getIsbn(), libro);
	}
}