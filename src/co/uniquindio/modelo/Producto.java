package co.uniquindio.modelo;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Luis Felipe Toro - Santiago Poveda Garcia
 *
 */
public class Producto implements Comparable<Producto> {

	String nombreProducto;
	String categoriaProducto;
	Date fechaHoraProducto;
	String usuario;
	ArrayList<Comentarios> listaComentarios = new ArrayList<>();
	ArrayList<Usuario> listaLikesProducto = new ArrayList<>();
	

	public Producto(String nombreProducto, String categoriaProducto, Date fechaHoraProducto, String usuario) {
		super();
		this.nombreProducto = nombreProducto;
		this.categoriaProducto = categoriaProducto;
		this.fechaHoraProducto = fechaHoraProducto;
		this.usuario = usuario;
		this.listaComentarios = new ArrayList<>();
		this.listaLikesProducto = new ArrayList<>();
	}

	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(String categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public Date getFechaHoraProducto() {
		return fechaHoraProducto;
	}

	public void setFechaHoraProducto(Date fechaHoraProducto) {
		this.fechaHoraProducto = fechaHoraProducto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public ArrayList<Comentarios> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(ArrayList<Comentarios> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	public ArrayList<Usuario> getListaLikesProducto() {
		return listaLikesProducto;
	}

	public void setListaLikesProducto(ArrayList<Usuario> listaLikesProducto) {
		this.listaLikesProducto = listaLikesProducto;
	}

	@Override
	public int compareTo(Producto o) {
		// TODO Auto-generated method stub
		return getFechaHoraProducto().compareTo(o.getFechaHoraProducto());
	}

	@Transient
	public String getSize() {
		String cast = String.valueOf(listaLikesProducto.size());
		return cast;
	}
}
