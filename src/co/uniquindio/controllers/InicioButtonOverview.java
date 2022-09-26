package co.uniquindio.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import co.uniquindio.singleton.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class InicioButtonOverview implements Initializable {

	@FXML
	JFXButton creadoRed;
	ModelFactoryController singleton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		singleton = ModelFactoryController.getInstance();

		if (singleton.getRedSocial().grafo2List().size() > 0) {
			creadoRed.setVisible(false);
		} else {
			creadoRed.setVisible(true);
		}
	}

	public void crearRed() {
		singleton.getRedSocial().quemado();
		creadoRed.setVisible(false);

		String tilte = "Red creada!";
		String message = "Existoso!";
		tray.notification.TrayNotification tray = new TrayNotification();
		AnimationType type = AnimationType.POPUP;
		tray.setAnimationType(type);
		tray.setTitle(tilte);
		tray.setMessage(message);
		tray.setNotificationType(NotificationType.SUCCESS);
		tray.showAndDismiss(Duration.millis(2000));
	}

}
