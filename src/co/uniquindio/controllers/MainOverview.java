package co.uniquindio.controllers;

import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainOverview implements Initializable {

	@FXML
	private static MainApp red;
	public Pane context;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		red = new MainApp();
	}

	public void InicioOnAction() throws IOException {
		setUi("InicioButtonOverview");
		new FadeIn(context).play();
	}

	public void btnMuro() throws IOException {
		setUi("MuroOverview");
		new FadeIn(context).play();
	}

	public void btnZonaCreado() throws IOException {
		setUi("ZonaCreadoOverview");
		new FadeIn(context).play();
	}

	public void btnInfo() throws IOException {
		setUi("InfoOverview");
		new FadeIn(context).play();
	}

	public void btnVerGrafo() throws Exception {

		try {
			GraficadorController graficadorController = new GraficadorController();
			graficadorController.start(new Stage());
		} catch (Exception e) {
			String tilte = "Error!";
			String message = "¿Que haces?!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.ERROR);
			tray.showAndDismiss(Duration.millis(1000));
		}

	}

	public void btnSalir() {
		Stage stage = (Stage) context.getScene().getWindow();
		stage.close();
	}

	public static MainApp getRed() {
		return red;
	}

	private void setUi(String location) throws IOException {
		context.getChildren().clear();
		context.getChildren().add(FXMLLoader.load(this.getClass().getResource(location + ".fxml")));

	}

}