package co.uniquindio.controllers;

import animatefx.animation.*;
import co.uniquindio.singleton.ModelFactoryController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.util.Objects;
import static javafx.scene.paint.Color.TRANSPARENT;

public class MainApp extends Application {

	@FXML
	ModelFactoryController modelFactoryController;
	public Pane context;

	public MainApp() {
		modelFactoryController = new ModelFactoryController();
	}

	public static void main(String[] args) {

		launch(args);
	}

	Parent root;
	double xOffset, yOffset;

	@Override
	public void start(Stage primaryStage) {
		try {
			root = FXMLLoader.load(Objects.requireNonNull(MainApp.class.getResource("PresentacionOverview.fxml")));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
			new FadeInDownBig(root).play();
			scene.setFill(TRANSPARENT);
			root.setOnMousePressed(event -> {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			});
			root.setOnMouseDragged(event -> {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			});

			String tilte = "Bienvenido";
			String message = "BIENVENIDO";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.SUCCESS);
			tray.showAndDismiss(Duration.millis(3000));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ModelFactoryController getModelFactoryController() {
		return modelFactoryController;
	}

	

	
}
