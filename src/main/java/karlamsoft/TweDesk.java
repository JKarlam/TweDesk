package karlamsoft;

import java.io.File;

import controllers.ControllerPinPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TweDesk extends Application {
	
	public static AppUser appUser;
	
	//JavaFX
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.setTitle("TweDesk");
			
			FXMLLoader layoutFXML = new FXMLLoader(getClass().getResource("/frames/PinPane.fxml"));
			ControllerPinPane controllerPinPane = new ControllerPinPane(primaryStage);
			layoutFXML.setController(controllerPinPane);
			Pane layout = layoutFXML.load();
			layout.getStyleClass().add("/frames/application.css");
			
			Scene scene = new Scene(layout);
			primaryStage.setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File cache = new File("./cache");
		if(!cache.exists() || !cache.isDirectory()) cache.mkdir();
		launch(args);
	}

}
