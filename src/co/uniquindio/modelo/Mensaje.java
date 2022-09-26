package co.uniquindio.modelo;

import java.util.Date;

public class Mensaje implements Comparable<Mensaje> {

	Usuario inicio;
	Usuario destino;
	Date fecha;
	String mensaje;
	Boolean leido;

	public Mensaje(Usuario inicio, Usuario destino, String mensaje, Date fecha, Boolean leido) {
		super();
		this.inicio = inicio;
		this.destino = destino;
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.leido = leido;
	}

	public Mensaje() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario getInicio() {
		return inicio;
	}

	public void setInicio(Usuario inicio) {
		this.inicio = inicio;
	}

	public Usuario getDestino() {
		return destino;
	}

	public void setDestino(Usuario destino) {
		this.destino = destino;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getLeido() {
		return leido;
	}

	public void setLeido(Boolean leido) {
		this.leido = leido;
	}

	@Override
	public int compareTo(Mensaje o) {
		// TODO Auto-generated method stub
		return getFecha().compareTo(o.getFecha());
	}

}
