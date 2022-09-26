package co.uniquindio.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import co.uniquindio.modelo.Mensaje;
import co.uniquindio.modelo.Usuario;
import co.uniquindio.singleton.ModelFactoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class MensajeController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		singleton = ModelFactoryController.getInstance();

		observableUsuario = FXCollections.observableArrayList(lista);
		this.tblUsuarios.setItems(observableUsuario);
		this.colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));

		tblUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				refresh();
			}
		});

	}

	private Usuario online = new Usuario();
	ModelFactoryController singleton;
	@FXML
	private Label label;

	@FXML
	private JFXButton btnEnvio;

	@FXML
	private JFXTextField tfMensaje;

	@FXML
	private JFXTextArea textAreaMensajes;

	private List<Usuario> lista = new ArrayList<>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private TableView<Usuario> tblUsuarios = new TableView();
	@FXML
	private TableColumn<Usuario, String> colNombre = new TableColumn<>("nombreUsuario");

	@FXML
	private ObservableList<Usuario> observableUsuario;

	public void setTabla(List<Usuario> array) {
		lista = array;
	}

	public List<Usuario> getLista() {
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public void refreshOnline(Usuario usuario) {
		online = usuario;
	}

	public void sendMessage() {

		List<Mensaje> listAux = new ArrayList<>();

		try {
			String msj = tfMensaje.getText();
			Usuario destino = returnDestino(tblUsuarios.getSelectionModel().getSelectedItem().getNombreUsuario());

			Date fecha = new Date();

			online.getListaMensajes().add(new Mensaje(online, destino, msj, fecha, false));

			String tilte = "Exitoso";
			String message = "Mensaje Enviado!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.FADE;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.SUCCESS);
			tray.showAndDismiss(Duration.millis(1000));

			String aux = "";

			for (int i = 0; i < online.getListaMensajes().size(); i++) {
				if (online.getListaMensajes().get(i).getDestino().equals(destino)) {
					listAux.add(online.getListaMensajes().get(i));
				}
			}

			for (int i = 0; i < destino.getListaMensajes().size(); i++) {
				if (destino.getListaMensajes().get(i).getDestino().equals(online)) {
					listAux.add(destino.getListaMensajes().get(i));
				}
			}

			listAux.sort(Comparator.comparing(o -> o.getFecha()));

			for (int i = 0; i < listAux.size(); i++) {
				aux += listAux.get(i).getInicio().getNombreUsuario() + ": ";
				aux += listAux.get(i).getMensaje();
				aux += "\n";
			}
			textAreaMensajes.setText(aux);

			tfMensaje.setText("");

		} catch (Exception e) {

			String tilte = "Error";
			String message = "Recuerde seleccionar el destinario!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.FADE;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}

	}

	public Usuario returnDestino(String nombre) {

		Usuario usuario2 = new Usuario();

		for (Usuario usuario : singleton.getRedSocial().getGrafoVendedores().getAllVertices()) {
			if (nombre.equals(usuario.getNombreUsuario())) {
				usuario2 = usuario;
				return usuario2;
			}
		}

		return usuario2;
	}

	public void refresh() {

		String aux = "";

		List<Mensaje> listAux = new ArrayList<>();

		Usuario destino = returnDestino(tblUsuarios.getSelectionModel().getSelectedItem().getNombreUsuario());

		for (int i = 0; i < online.getListaMensajes().size(); i++) {
			if (online.getListaMensajes().get(i).getDestino().equals(destino)) {
				listAux.add(online.getListaMensajes().get(i));
			}
		}

		for (int i = 0; i < destino.getListaMensajes().size(); i++) {
			if (destino.getListaMensajes().get(i).getDestino().equals(online)) {
				listAux.add(destino.getListaMensajes().get(i));
			}
		}

		listAux.sort(Comparator.comparing(o -> o.getFecha()));

		for (int i = 0; i < listAux.size(); i++) {
			aux += listAux.get(i).getInicio().getNombreUsuario() + ": ";
			aux += listAux.get(i).getMensaje();
			aux += "\n";
		}
		textAreaMensajes.setText(aux);
	}
}