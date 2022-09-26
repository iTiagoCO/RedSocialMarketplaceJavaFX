package co.uniquindio.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import co.uniquindio.modelo.Producto;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;

public class MuroController implements Initializable {

	@FXML
	private JFXTextArea textAreaComentarios;
	@FXML
	private ImageView imagenComent;

	@FXML
	private ImageView imagenMessenger;
	@FXML
	private Text numLikes;
	@FXML
	JFXButton btnRenovar;

	@FXML
	private JFXComboBox<String> comboVendedores;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ScrollPane scrollSugerencias;

	@FXML
	private GridPane gridPane;

	@FXML
	private GridPane gridSugerencias;

	@FXML
	ModelFactoryController singleton;

	public MuroController muroController;

	private List<Usuario> listaUsuarios = new ArrayList<>();

	private List<Producto> listaProductos = new ArrayList<>();

	private List<Producto> getDatos() throws IOException {

		ArrayList<Producto> listaProductos = new ArrayList<>();

		Usuario usuario = new Usuario();
		usuario = retornarConectado();

		for (int i = 0; i < usuario.getListaProductosUsuario().size(); i++) {
			Producto producto = new Producto();
			producto.setNombreProducto(usuario.getListaProductosUsuario().get(i).getNombreProducto());
			producto.setCategoriaProducto(usuario.getListaProductosUsuario().get(i).getCategoriaProducto());
			producto.setFechaHoraProducto(usuario.getListaProductosUsuario().get(i).getFechaHoraProducto());
			producto.setUsuario(usuario.getListaProductosUsuario().get(i).getUsuario());
			producto.setListaLikesProducto(usuario.getListaProductosUsuario().get(i).getListaLikesProducto());

			/*
			 * AQUI
			 */

			listaProductos.add(producto);
		}

		for (Usuario usuarioConectado : singleton.getRedSocial().getGrafoVendedores().getNeighbors(usuario)) {

			ArrayList<Producto> lista = new ArrayList<>();
			lista = usuarioConectado.getListaProductosUsuario();

			for (int j = 0; j < lista.size(); j++) {
				Producto producto = new Producto();
				producto.setNombreProducto(lista.get(j).getNombreProducto());
				producto.setCategoriaProducto(lista.get(j).getCategoriaProducto());
				producto.setFechaHoraProducto(lista.get(j).getFechaHoraProducto());
				producto.setUsuario(lista.get(j).getUsuario());
				producto.setListaLikesProducto(lista.get(j).getListaLikesProducto());

				listaProductos.add(producto);
			}

		}

		return listaProductos;
	}

	@FXML
	private List<Usuario> getDatosSugerencias() {

		listaUsuarios = new ArrayList<Usuario>(new LinkedHashSet<Usuario>(listaUsuarios));

		listaUsuarios.clear();

		for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

			if (usuario.getNombreUsuario().equals(comboVendedores.getSelectionModel().getSelectedItem()) == false) {
				listaUsuarios.add(usuario);
			}

		}

		return listaUsuarios;
	}

	@FXML
	public void renovarSugerencias() throws IOException {

		muroController = this;

		listaUsuarios.clear();
		listaUsuarios.addAll(getDatosSugerencias());
		int column = 0;
		int row = 1;
		gridSugerencias.getChildren().clear();

		for (int i = 0; i < listaUsuarios.size(); i++) {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("SugerenciaOverview.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();

			sugerenciaController sugerenciaController = fxmlLoader.getController();
			sugerenciaController.setDatos(listaUsuarios.get(i),
					comboVendedores.getSelectionModel().getSelectedItem().toString());
			sugerenciaController.refreshMuro(muroController);

			gridSugerencias.add(anchorPane, column, row++);

			gridSugerencias.setMinWidth(Region.USE_COMPUTED_SIZE);
			gridSugerencias.setPrefWidth(Region.USE_COMPUTED_SIZE);
			gridSugerencias.setMaxWidth(Region.USE_COMPUTED_SIZE);

			gridSugerencias.setMinHeight(Region.USE_COMPUTED_SIZE);
			gridSugerencias.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gridSugerencias.setMaxHeight(Region.USE_COMPUTED_SIZE);

			GridPane.setMargin(anchorPane, new Insets(0));

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
		agregarUsuariosCombo();
		listaUsuarios = new ArrayList<Usuario>(new LinkedHashSet<Usuario>(listaUsuarios));
		textAreaComentarios.setText("Aun sin comentarios :(");

		imagenMessenger.setPickOnBounds(true);

		imagenMessenger.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event event) {

				if (comboVendedores.getSelectionModel().getSelectedItem() == null) {

					String tilte = "Error";
					String message = "Inicie en un perfil primero!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.FADE;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.ERROR);
					tray.showAndDismiss(Duration.millis(1000));

				} else {

					Stage stage = new Stage();
					try {
						MensajeController mensajeController = new MensajeController();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("MensajeOverview.fxml"));

						mensajeController.setTabla(getDatosSugerencias());
						mensajeController.refreshOnline(retornarConectado());
						loader.setController(mensajeController);

						AnchorPane root = (AnchorPane) loader.load();
						Scene scene = new Scene(root);

						stage.setScene(scene);
						stage.show();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		});
	}

	@FXML
	public Usuario retornarConectado() throws IOException {

		Usuario usuarioRetorno = null;
		String nombre = comboVendedores.getSelectionModel().getSelectedItem();

		for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {
			if (nombre.equals(usuario.getNombreUsuario())) {
				usuarioRetorno = usuario;
				return usuarioRetorno;
			}
		}

		return usuarioRetorno;

	}

	@FXML
	public void renovar() throws Exception {


		muroController = this;

		textAreaComentarios.setText("");
		refreshComentarios();
		refreshLikes();
		renovarSugerencias();

		listaProductos.clear();

		listaProductos = getDatos();

		listaProductos.sort(Comparator.comparing(o -> o.getFechaHoraProducto()));

		int column = 0;
		int row = 1;

		gridPane.getChildren().clear();

		for (int i = listaProductos.size() - 1; i >= 0; i--) {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ProductoOverview.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();

			ProductoController productoController = fxmlLoader.getController();
			productoController.refreshOnline(retornarConectado(), muroController);
			productoController.setDatos(listaProductos.get(i), retornarConectado());
			productoController.refreshOnline(retornarConectado(), muroController);

			if (column == 2) {
				column = 0;
				row++;
			}

			gridPane.add(anchorPane, column++, row);

			gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
			gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
			gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

			gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
			gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
			gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

			GridPane.setMargin(anchorPane, new Insets(0));

		}

	}

	@FXML
	public void agregarUsuariosCombo() {

		for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {
			comboVendedores.getItems().add(usuario.getNombreUsuario());
		}

	}

	public JFXComboBox<String> getComboVendedores() {
		return comboVendedores;
	}

	public void refreshLikes() throws IOException {

		Usuario usuario = retornarConectado();

		int cont = 0;

		cont = usuario.getListaLikes().size();
		String aux;
		aux = String.valueOf(cont);
		numLikes.setText(aux);

	}

	public void refreshComentarios() throws IOException {

		String aux = "";
		Usuario usuario = retornarConectado();

		for (int i = 0; i < usuario.getListaComentUsuario().size(); i++) {

			aux += usuario.getListaComentUsuario().get(i).getNombreComentador().getNombreUsuario() + ": ";
			aux += usuario.getListaComentUsuario().get(i).getTextoComentario() + "";
			aux += "\n";

		}

		textAreaComentarios.setText(aux);

	}

}
