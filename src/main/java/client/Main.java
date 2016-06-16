package client.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = FXMLLoader.load(getClass().getResource("LogWindow.fxml"));
			Scene scene = new Scene(root,280,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Logowanie");
			TextField ipTextField = (TextField) scene.lookup("#userIPTest");
			ipTextField.setText(TcpClient.getCurrentIPAddress());
			ipTextField.requestFocus();
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
