package co.uniquindio.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import co.uniquindio.modelo.Producto;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ProductoController implements Initializable {

	@FXML
	private Label nombreProducto;

	@FXML
	private Label categoriaProducto;

	@FXML
	private Label fechaProducto;
	@FXML
	private Label vendedorLabel;
	@FXML
	private ImageView imagenLike;

	@FXML
	private Text numLikes;

	@FXML
	private ImageView imagenComentarios;
	ModelFactoryController singleton;
	private Producto producto;
	private Usuario online;
	private MuroController muroController;

	public void setDatos(Producto producto, Usuario usuario) {
		online = new Usuario();
		this.producto = producto;
		nombreProducto.setText(producto.getNombreProducto());
		categoriaProducto.setText(producto.getCategoriaProducto());
		fechaProducto.setText(producto.getFechaHoraProducto().toString());
		vendedorLabel.setText(producto.getUsuario());

		int aux = 0;

		if (producto.getListaLikesProducto() != null) {
			aux = producto.getListaLikesProducto().size();
			String aux1 = String.valueOf(aux);
			numLikes.setText(aux1);
		} else {
			numLikes.setText("0");
		}

		online = usuario;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();

		imagenComentarios.setPickOnBounds(true);

		imagenComentarios.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {

				Stage stage = new Stage();
				try {

					ComentarioController comentarioController = new ComentarioController();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ComentarioOverview.fxml"));
					comentarioController.refresh(online, producto);

					loader.setController(comentarioController);

					AnchorPane root = (AnchorPane) loader.load();
					Scene scene = new Scene(root);

					stage.setScene(scene);
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		imagenLike.setPickOnBounds(true);

		imagenLike.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event arg0) {

				if (producto.getListaLikesProducto().contains(online)) {

					for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

						if (usuario.getNombreUsuario().equals(producto.getUsuario())) {

							for (int i = 0; i < usuario.getListaProductosUsuario().size(); i++) {

								if (usuario.getListaProductosUsuario().get(i).getNombreProducto()
										.equals(producto.getNombreProducto())) {
									usuario.getListaProductosUsuario().get(i).getListaLikesProducto().remove(online);

									System.out.println(producto.getNombreProducto());

									String tilte = "Dis-Like";
									String message = "Ya no te gusta el producto!";
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
						}

					}

				} else {

					for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

						if (usuario.getNombreUsuario().equals(producto.getUsuario())) {

							for (int i = 0; i < usuario.getListaProductosUsuario().size(); i++) {

								if (usuario.getListaProductosUsuario().get(i).getNombreProducto()
										.equals(producto.getNombreProducto())) {

									usuario.getListaProductosUsuario().get(i).getListaLikesProducto().add(online);

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
						}

					}

				}

			}

		});
	}

	public void refreshOnline(Usuario retornarConectado, MuroController muroController1) {
		// TODO Auto-generated method stub
		online = new Usuario();
		online = retornarConectado;
		muroController = muroController1;
	}

}
