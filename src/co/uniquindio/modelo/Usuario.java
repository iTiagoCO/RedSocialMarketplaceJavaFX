package co.uniquindio.modelo;

import java.util.ArrayList;

public class Usuario {

	String nombreUsuario;
	ArrayList<Producto> listaProductosUsuario;
	ArrayList<Comentarios> listaComentUsuario;
	ArrayList<Usuario> listaLikes;
	ArrayList<Mensaje> listaMensajes;

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombreUsuario) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.listaProductosUsuario = new ArrayList<>();
		this.listaComentUsuario = new ArrayList<>();
		this.listaLikes = new ArrayList<>();
		this.listaMensajes = new ArrayList<>();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public ArrayList<Comentarios> getListaComentUsuario() {
		return listaComentUsuario;
	}

	public void setListaComentUsuario(ArrayList<Comentarios> listaComentUsuario) {
		this.listaComentUsuario = listaComentUsuario;
	}

	public ArrayList<Usuario> getListaLikes() {
		return listaLikes;
	}

	public void setListaLikes(ArrayList<Usuario> listaLikes) {
		this.listaLikes = listaLikes;
	}

	public ArrayList<Producto> getListaProductosUsuario() {
		return listaProductosUsuario;
	}

	public void setListaProductosUsuario(ArrayList<Producto> listaProductosUsuario) {
		this.listaProductosUsuario = listaProductosUsuario;
	}

	public ArrayList<Mensaje> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(ArrayList<Mensaje> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public String printInitials() {
		String aux = "";
		if (nombreUsuario.length() == 0)
			return "";

		// split the string using 'space'
		// and print the first character of every word
		String words[] = nombreUsuario.split(" ");
		for (String word : words) {
			aux += Character.toUpperCase(word.charAt(0)) + " ";
		}
		return aux;
	}
	
	

}
