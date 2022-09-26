package co.uniquindio.controllers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import co.uniquindio.modelo.Producto;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class InfoOverviewController implements Initializable {

	@FXML
	private JFXComboBox<String> comboBoxP1;

	@FXML
	private JFXComboBox<String> comboBoxP2;

	@FXML
	private TextArea TextAreaM1;

	@FXML
	private JFXDatePicker DatePickerR1;

	@FXML
	private JFXDatePicker DatePickerR2;

	@FXML
	private TextArea TextAreaM2;

	@FXML
	private JFXComboBox<String> comboBoxM3;

	@FXML
	private TextArea TextAreaM3;

	@FXML
	private JFXComboBox<String> comboBoxM4;

	@FXML
	private TextArea TextAreaM4;

	@FXML
	ObservableList<Producto> observableProductos;

	@FXML
	private TableView<Producto> tableViewTop = new TableView<>();;

	@FXML
	private TableColumn<Producto, String> columnaNombre = new TableColumn<>("nombreProducto");

	@FXML
	private TableColumn<Producto, String> columnaLikes = new TableColumn<>("size");

	ModelFactoryController singleton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			// TODO Auto-generated method stub
			singleton = ModelFactoryController.getInstance();
			agregarUsuariosCombo();

			ArrayList<Producto> unique = singleton.getRedSocial().returnAllProducts();

			Collections.sort(unique, new Comparator<Producto>() {
				@Override
				public int compare(Producto p1, Producto p2) {
					return new Integer(p1.getListaLikesProducto().size())
							.compareTo(new Integer(p2.getListaLikesProducto().size()));
				}
			});

			ArrayList<Producto> aux = new ArrayList<>();
			int cont = 0;
			for (int i = unique.size() - 1; i >= 0; i--) {
				if (cont != 10) {
					aux.add(unique.get(i));
					cont++;
				} else {
					break;
				}

			}

			observableProductos = FXCollections.observableArrayList(aux);
			this.tableViewTop.setItems(observableProductos);
			this.columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
			this.columnaLikes.setCellValueFactory(new PropertyValueFactory<>("size"));

		} catch (Exception e) {
			String tilte = "Error!";
			String message = "Revisa que haces!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}

	}

	public void cantidadMensajes() {

		try {

			String p1 = comboBoxP1.getSelectionModel().getSelectedItem();
			String p2 = comboBoxP2.getSelectionModel().getSelectedItem();

			if (p1 != null && p2 != null) {
				TextAreaM1.setText(singleton.getRedSocial().cantMsj(p1, p2));
			}
		} catch (Exception e) {
			String tilte = "Error!";
			String message = "Revisa que haces!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}
	}

	public void agregarUsuariosCombo() {

		try {
			for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {
				comboBoxP1.getItems().add(usuario.getNombreUsuario());
				comboBoxP2.getItems().add(usuario.getNombreUsuario());
				comboBoxM3.getItems().add(usuario.getNombreUsuario());
				comboBoxM4.getItems().add(usuario.getNombreUsuario());
			}
		} catch (Exception e) {
			String tilte = "Error!";
			String message = "Revisa que haces!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}

	}

	public void productosRango() {

		try {
			int cant = 0;
			LocalDate localDate = DatePickerR1.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date rango1 = Date.from(instant);

			LocalDate localDate2 = DatePickerR2.getValue();
			Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
			Date rango2 = Date.from(instant2);

			ArrayList<Producto> unique = singleton.getRedSocial().returnAllProducts();

			for (int i = 0; i < unique.size(); i++) {

				if (unique.get(i).getFechaHoraProducto().after(rango1)
						&& unique.get(i).getFechaHoraProducto().before(rango2)) {

					cant++;
				}

			}

			String copy = String.valueOf(cant);
			TextAreaM2.setText(copy);

		} catch (Exception e) {
			String tilte = "Error!";
			String message = "Revisa que haces!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}

	}

	public void returnCantProduct() {

		try {
			String name = comboBoxM3.getSelectionModel().getSelectedItem();
			TextAreaM3.setText(singleton.getRedSocial().sizeProductUsuario(name));
		} catch (Exception e) {
			String tilte = "Error!";
			String message = "Revisa que haces!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}
	}

	public void returnSizeUsuarios() {
		try {
			String name = comboBoxM4.getSelectionModel().getSelectedItem();
			TextAreaM4.setText(singleton.getRedSocial().sizeUsuarios(name));
		} catch (Exception e) {
			String tilte = "Error!";
			String message = "Revisa que haces!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}

	}

}
