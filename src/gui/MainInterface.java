package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.ControllerTelaPrincipal;

public class MainInterface extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"../gui/TelaPrincipal.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		ControllerTelaPrincipal controller = fxmlLoader
				.<ControllerTelaPrincipal> getController();
		primaryStage.setTitle("MoneySaver");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		controller.setStage(primaryStage);
	}
}
