package co.uniquindio.modelo;

import java.util.ArrayList;
import java.util.Date;
import co.uniquindio.estructuras.Grafo;

/**
 * @author Luis Felipe Toro - Santiago Poveda Garcia
 *
 */
public class Red {

	Grafo<Usuario> grafo;

	/**
	 * Constructor.
	 */
	public Red() {
		grafo = new Grafo<Usuario>();

	}

	public void quemado() {

		Date fecha2 = new Date();

		fecha2.setTime(1);
		Date fecha = new Date();
		// Usuario1
		Usuario usuario = new Usuario("Luis Armando Moreno");
		// Productos Usuario1
		usuario.getListaProductosUsuario()
				.add(new Producto("Secadora", "Electrodomesticos", fecha, usuario.getNombreUsuario()));
		usuario.getListaProductosUsuario()
				.add(new Producto("DiscoDuro 300GB", "Electrodomesticos", fecha2, usuario.getNombreUsuario()));

		usuario.getListaProductosUsuario()
				.add(new Producto("Tarjeta Grafica NVIDIA", "Tecnologia", fecha2, usuario.getNombreUsuario()));
		usuario.getListaProductosUsuario()
				.add(new Producto("SmartWatch GEN 3", "Tecnologia", fecha2, usuario.getNombreUsuario()));
		usuario.getListaProductosUsuario()
				.add(new Producto("Cargador Iphone 8Plus", "Tecnologia", fecha2, usuario.getNombreUsuario()));

		// Usuario2
		Usuario usuario2 = new Usuario("Felipe Toro");
		// Productos usuario2
		usuario2.getListaProductosUsuario()
				.add(new Producto("Plancha", "Electrodomesticos", fecha2, usuario2.getNombreUsuario()));
		usuario2.getListaProductosUsuario()
				.add(new Producto("Ventilador", "Electrodomesticos", fecha2, usuario2.getNombreUsuario()));
		usuario2.getListaProductosUsuario().add(
				new Producto("Cama retractil 1.80x1.40", "Electrodomesticos", fecha2, usuario2.getNombreUsuario()));
		usuario2.getListaProductosUsuario()
				.add(new Producto("Arrocera 2Libras", "Electrodomesticos", fecha2, usuario2.getNombreUsuario()));
		usuario2.getListaProductosUsuario()
				.add(new Producto("Freidora", "Electrodomesticos", fecha2, usuario2.getNombreUsuario()));
		Producto prod = new Producto("Lampara", "Electrodomesticos", fecha, usuario2.getNombreUsuario());
		usuario2.getListaProductosUsuario().add(prod);

		// usuario 3
		Usuario usuario3 = new Usuario("Perein");
		usuario3.getListaProductosUsuario().add(new Producto("AYATE 110", "Vehiculos", fecha2, "Perein"));
		usuario3.listaLikes.add(usuario3);

		// Añadimos los vertices
		grafo.addVertex(usuario3);
		grafo.addVertex(usuario);
		grafo.addVertex(usuario2);

		// Agregamos conexiones
		grafo.addEdge(usuario2, usuario3);
		grafo.addEdge(usuario, usuario2);

	}

	/**
	 * Retorna Array del Grafo
	 * 
	 * @return
	 */
	public ArrayList<Usuario> grafo2List() {

		ArrayList<Usuario> ArrayUsuarios = new ArrayList<>();

		for (Usuario usuario : this.grafo.getAllVertices()) {
			ArrayUsuarios.add(usuario);
		}

		return ArrayUsuarios;
	}

	public String printGraph() {

		String acumulador = "";

		for (Usuario usuario : this.grafo.getAllVertices()) {
			acumulador += usuario.getNombreUsuario() + ": ";
			for (Usuario usuario2 : this.grafo.getNeighbors(usuario)) {
				acumulador += " --> " + usuario2.getNombreUsuario();
			}
			acumulador += "\n";
		}

		return acumulador;

	}

	public Grafo<Usuario> getGrafoVendedores() {
		return grafo;
	}

	public void setGrafoVendedores(Grafo<Usuario> grafoVendedores) {
		this.grafo = grafoVendedores;
	}

	/**
	 * Retorna Cant MSJ entre 2 Usuarios, P1.P2
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public String cantMsj(String p1, String p2) {

		Usuario usuario0 = new Usuario();
		Usuario usuario1 = new Usuario();

		ArrayList<Mensaje> ArrayAux = new ArrayList<>();
		ArrayList<Mensaje> ArrayAux1 = new ArrayList<>();

		int cant = 0;

		for (Usuario usuario : this.grafo.getAllVertices()) {

			for (Usuario usuario2 : this.grafo.getNeighbors(usuario)) {

				if (usuario.getNombreUsuario().equals(p1) && usuario2.getNombreUsuario().equals(p2)) {
					usuario0 = usuario;
					usuario1 = usuario2;
					ArrayAux = usuario.getListaMensajes();
					ArrayAux1 = usuario2.getListaMensajes();
				}
			}

		}

		for (int i = 0; i < ArrayAux.size(); i++) {
			if (ArrayAux.get(i).getDestino().equals(usuario1)) {
				cant++;
			}
		}

		for (int i = 0; i < ArrayAux1.size(); i++) {
			if (ArrayAux1.get(i).getDestino().equals(usuario0)) {
				cant++;
			}
		}

		String cant1 = String.valueOf(cant);

		return cant1;
	}

	/**
	 * Retorna todos los productos para comparar
	 * 
	 * @return
	 */
	public ArrayList<Producto> returnAllProducts() {

		ArrayList<Producto> unique = new ArrayList<>();

		for (Usuario usuario : this.grafo.getAllVertices()) {
			ArrayList<Producto> array = usuario.getListaProductosUsuario();
			for (int i = 0; i < array.size(); i++) {
				unique.add(array.get(i));
			}
		}
		return unique;
	}

	public String sizeProductUsuario(String Name) {

		int cant = 0;

		for (Usuario usuario : this.grafo.getAllVertices()) {
			if (usuario.getNombreUsuario().equals(Name)) {
				cant = usuario.getListaProductosUsuario().size();
			}
		}
		String copy = String.valueOf(cant);
		return copy;

	}

	public String sizeUsuarios(String Name) {

		int cont = 0;

		for (Usuario usuario : this.grafo.getAllVertices()) {
			if (usuario.getNombreUsuario().equals(Name)) {

				for (Usuario usuario2 : this.grafo.getNeighbors(usuario)) {
					cont++;
				}

			}
		}
		String copy = String.valueOf(cont);
		return copy;

	}

}
