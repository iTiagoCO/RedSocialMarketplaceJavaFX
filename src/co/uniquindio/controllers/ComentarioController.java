package co.uniquindio.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import co.uniquindio.modelo.Comentarios;
import co.uniquindio.modelo.Producto;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ComentarioController implements Initializable {

	ModelFactoryController singleton;

	@FXML
	private JFXButton btnEnvio;

	@FXML
	private JFXTextField tfMensaje;

	@FXML
	private JFXTextArea textAreaMensajes;

	private Usuario online;
	private Usuario destino;
	private Producto producto;

	public void sendComment() {

		try {

			String msj = "";
			msj = tfMensaje.getText();
			

			for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

				if (usuario.getNombreUsuario().equals(producto.getUsuario())) {

					destino = usuario;

					for (int i = 0; i < usuario.getListaProductosUsuario().size(); i++) {

						if (usuario.getListaProductosUsuario().get(i).getNombreProducto()
								.equals(producto.getNombreProducto())) {

							usuario.getListaProductosUsuario().get(i).getListaComentarios()
									.add(new Comentarios(online, msj, destino));

							String tilte = "Exitoso";
							String message = "Comentario publicado!";
							tray.notification.TrayNotification tray = new TrayNotification();
							AnimationType type = AnimationType.FADE;
							tray.setAnimationType(type);
							tray.setTitle(tilte);
							tray.setMessage(message);
							tray.setNotificationType(NotificationType.SUCCESS);
							tray.showAndDismiss(Duration.millis(1000));

							loadComentarios();

						}
					}

				}
			}

			tfMensaje.setText("");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	public ComentarioController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		singleton = ModelFactoryController.getInstance();

		loadComentarios();
	}

	public void refresh(Usuario usuario, Producto product) {

		online = usuario;
		producto = product;

	}

	public void loadComentarios() {

		String aux = "";

		try {

			for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

				if (usuario.getNombreUsuario().equals(producto.getUsuario())) {

					for (int i = 0; i < usuario.getListaProductosUsuario().size(); i++) {

						for (int j = 0; j < usuario.getListaProductosUsuario().get(i).getListaComentarios()
								.size(); j++) {

							if (usuario.getListaProductosUsuario().get(i).getNombreProducto()
									.equals(producto.getNombreProducto())) {

								aux += usuario.getListaProductosUsuario().get(i).getListaComentarios().get(j)
										.getNombreComentador().getNombreUsuario() + ": ";
								aux += usuario.getListaProductosUsuario().get(i).getListaComentarios().get(j)
										.getTextoComentario();
								aux += "\n";

							}
						}

					}

					textAreaMensajes.setText(aux);

				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		}

	}

	public JFXTextArea getTextAreaMensajes() {
		return textAreaMensajes;
	}

	public void setTextAreaMensajes(JFXTextArea textAreaMensajes) {
		this.textAreaMensajes = textAreaMensajes;
	}

}
