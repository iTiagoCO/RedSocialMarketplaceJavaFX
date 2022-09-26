package co.uniquindio.modelo;

public class Comentarios {

	Usuario nombreComentador;
	Usuario nombreReceptor;
	String textoComentario;

	public Comentarios(Usuario nombreComentador, String textoComentario, Usuario nombreReceptor) {
		super();
		this.nombreComentador = nombreComentador;
		this.textoComentario = textoComentario;
		this.nombreReceptor = nombreReceptor;
	}

	public Comentarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Usuario getNombreComentador() {
		return nombreComentador;
	}

	public void setNombreComentador(Usuario nombreComentador) {
		this.nombreComentador = nombreComentador;
	}

	public String getTextoComentario() {
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}

	public Usuario getNombreReceptor() {
		return nombreReceptor;
	}

	public void setNombreReceptor(Usuario nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}

}
