package co.uniquindio.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import co.uniquindio.modelo.Comentarios;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class sugerenciaController implements Initializable {

	public Pane context;

	@FXML
	private Text text;
	@FXML
	private Text numLikes;

	@FXML
	MuroController muro;
	@FXML
	private ImageView imagenAgregar;
	@FXML
	private ImageView imagenLike;
	@FXML
	private ImageView imagenMessenger;
	@FXML
	private ImageView imagenComentarios;

	@FXML
	ModelFactoryController singleton;
	private MuroController muroController;
	private Usuario usuario;
	private Usuario online;

	public sugerenciaController() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		singleton = ModelFactoryController.getInstance();

		imagenAgregar.setPickOnBounds(true);

		imagenAgregar.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event event) {

				if (singleton.getRedSocial().getGrafoVendedores().isAdjacent(online, usuario) == false) {

					singleton.getRedSocial().getGrafoVendedores().addEdge(online, usuario);

					String tilte = "Guardado Exitoso";
					String message = "Enlace Creado!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.SUCCESS);
					tray.showAndDismiss(Duration.millis(3000));

					try {
						muroController.renovar();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setTitle("Confirmación");
					alert.setContentText(
							"¿Estas seguro de Eliminar a " + usuario.getNombreUsuario() + " de tu lista de Amigos?");
					Optional<ButtonType> action = alert.showAndWait();

					if (action.get() == ButtonType.OK) {

						singleton.getRedSocial().getGrafoVendedores().removeEdge(online, usuario);

						String tilte = "Atencion";
						String message = "Haz roto el enlace con tu amigo!";
						tray.notification.TrayNotification tray = new TrayNotification();
						AnimationType type = AnimationType.POPUP;
						tray.setAnimationType(type);
						tray.setTitle(tilte);
						tray.setMessage(message);
						tray.setNotificationType(NotificationType.NOTICE);
						tray.showAndDismiss(Duration.millis(3000));

						try {
							muroController.renovar();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			}
		});

		imagenComentarios.setPickOnBounds(true);

		imagenComentarios.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event event) {
				String mensaje = JOptionPane.showInputDialog("Ingrese el comentario:");

				if (mensaje == null) {

					String tilte = "Ups";
					String message = "Comentario Vacio!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.ERROR);
					tray.showAndDismiss(Duration.millis(2000));

					try {
						muroController.renovar();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					@SuppressWarnings("unused")
					Date date = new Date();

					usuario.getListaComentUsuario().add(new Comentarios(online, mensaje, usuario));

					String tilte = "OK";
					String message = "Comentario Publicado!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.SUCCESS);
					tray.showAndDismiss(Duration.millis(2000));

					try {
						muroController.renovar();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		imagenLike.setPickOnBounds(true);
		imagenLike.setOnMouseClicked(new EventHandler() {

			/*
			 * ARREGLAR AQUI
			 */
			@Override
			public void handle(Event event) {

				if (usuario.getListaLikes().contains(online)) {

					usuario.getListaLikes().remove(online);

					String tilte = "Dis-Like";
					String message = "Ya no te gusta!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.SUCCESS);
					tray.showAndDismiss(Duration.millis(1000));

					try {
						muroController.renovar();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					usuario.getListaLikes().add(online);

					String tilte = "Like";
					String message = "Te gusta!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.SUCCESS);
					tray.showAndDismiss(Duration.millis(1000));

					try {
						muroController.renovar();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

	}

	public void setDatos(Usuario usuario, String nombreActual) throws IOException {

		online = new Usuario();

		for (Usuario usuarioOriginal : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {
			if (usuarioOriginal.getNombreUsuario().equals(nombreActual)) {
				online = usuarioOriginal;
			}
		}

		if (singleton.getRedSocial().getGrafoVendedores().isAdjacent(online, usuario) == false) {

			this.usuario = usuario;
			text.setText(usuario.getNombreUsuario());

			int cont = usuario.getListaLikes().size();

			String aux = String.valueOf(cont);
			numLikes.setText(aux);

			InputStream stream = new FileInputStream("resources/follow.png");
			Image image = new Image(stream);
			// Creating the image view
			// Setting image to the image view
			imagenAgregar.setImage(image);

		} else {
			this.usuario = usuario;
			text.setText(usuario.getNombreUsuario());

			int cont = usuario.getListaLikes().size();

			String aux = String.valueOf(cont);
			numLikes.setText(aux);

			InputStream stream = new FileInputStream("resources/favpng_download-user.png");
			Image image = new Image(stream);
			// Creating the image view
			// Setting image to the image view
			imagenAgregar.setImage(image);
			// Setting the image view parameters

		}

	}

	public void refreshMuro(MuroController muroController2) {

		muroController = muroController2;

	}

}
