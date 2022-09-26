package co.uniquindio.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import co.uniquindio.modelo.Producto;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;

public class ZonaCreadoController implements Initializable {
	@FXML
	JFXButton btnSave;
	@FXML
	JFXButton btnSaveProduct;
	@FXML
	ModelFactoryController singleton;

	@FXML
	JFXTextField nombreVendedor;
	@FXML
	JFXTextField nombreProducto;
	@FXML
	JFXComboBox<String> comboVendedor;
	@FXML
	JFXComboBox<String> categoriaProducto;
	@FXML
	ObservableList<Usuario> observableUsuarios;
	@FXML
	private TableView<Usuario> tablaUsuarios = new TableView<>();
	@FXML
	private TableColumn<Usuario, String> column_NombreUsuario = new TableColumn<>("nombreUsuario");

	public ZonaCreadoController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
		nombreVendedor.setText(null);

		categoriaProducto.getItems().addAll("Vehiculos", "Supermercado", "Electrodomesticos", "Tecnologia",
				"Herramientas", "Moda", "Servicios", "Juguetes", "Bebes", "Deporte");

		agregarUsuariosCombo();

		observableUsuarios = FXCollections.observableArrayList(singleton.getRedSocial().grafo2List());
		this.tablaUsuarios.setItems(observableUsuarios);
		this.column_NombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
	}

	@FXML
	public void insertarVendedor() {

		Boolean pivot = false;

		if (nombreVendedor.getText() != null) {
			try {

				for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

					if (usuario.getNombreUsuario().equals(nombreVendedor.getText())) {

						pivot = true;
						break;
					} else {
						pivot = false;
					}

				}

				if (pivot == true) {

					String tilte = "ERROR";
					String message = "Usuario Repetido!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.WARNING);
					tray.showAndDismiss(Duration.millis(3000));

				}

				if (pivot == false) {
					singleton.getRedSocial().getGrafoVendedores().addVertex(new Usuario(nombreVendedor.getText()));

					String tilte = "Guardado Exitoso";
					String message = "Usuario Agregado!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.SUCCESS);
					tray.showAndDismiss(Duration.millis(3000));
					nombreVendedor.setText(null);
					actualizarTabla();
				}

			} catch (IllegalArgumentException e) {
				String tilte = "ERROR";
				String message = "Revise los Campos!";
				tray.notification.TrayNotification tray = new TrayNotification();
				AnimationType type = AnimationType.POPUP;
				tray.setAnimationType(type);
				tray.setTitle(tilte);
				tray.setMessage(message);
				tray.setNotificationType(NotificationType.ERROR);
				tray.showAndDismiss(Duration.millis(3000));
			}
		} else {

			String tilte = "ERROR";
			String message = "Campo Vacio!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.WARNING);
			tray.showAndDismiss(Duration.millis(3000));

		}

	}

	public void insertarProducto() {

		try {

			for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {

				if (usuario.getNombreUsuario().equals(comboVendedor.getSelectionModel().getSelectedItem())) {

					Date dNow = new Date();

					String aux = comboVendedor.getSelectionModel().getSelectedItem().toString();

					usuario.getListaProductosUsuario().add(new Producto(nombreProducto.getText(),
							categoriaProducto.getSelectionModel().getSelectedItem(), dNow, aux));

					String tilte = "Exitoso";
					String message = "Producto Agregado a: " + comboVendedor.getSelectionModel().getSelectedItem()
							+ " con exito!";
					tray.notification.TrayNotification tray = new TrayNotification();
					AnimationType type = AnimationType.POPUP;
					tray.setAnimationType(type);
					tray.setTitle(tilte);
					tray.setMessage(message);
					tray.setNotificationType(NotificationType.SUCCESS);
					tray.showAndDismiss(Duration.millis(3000));

					comboVendedor.getSelectionModel().clearSelection();
					nombreProducto.setText("");
					categoriaProducto.getSelectionModel().clearSelection();
				}

			}
		} catch (Exception e) {

			String tilte = "ERROR";
			String message = "Error, Revise los Campos!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(3000));
		}

	}

	@FXML
	public void actualizarTabla() {
		observableUsuarios.clear();
		observableUsuarios = FXCollections.observableArrayList(singleton.getRedSocial().grafo2List());

		this.tablaUsuarios.setItems(observableUsuarios);
		this.column_NombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
	}

	public void agregarUsuariosCombo() {

		for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {
			comboVendedor.getItems().add(usuario.getNombreUsuario());
		}

	}

}
