package co.uniquindio.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class PresentacionController {
	public AnchorPane root;

	public void AccesoAction() throws IOException {

		Stage window = (Stage) this.root.getScene().getWindow();
		window.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("InicioOverview.fxml"))));

		window.centerOnScreen();

	}

}
