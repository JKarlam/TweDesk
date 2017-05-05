package karlamsoft;

import java.io.IOException;

import controllers.ControllerUserHome;
import controllers.ControllerUserTimeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tweet.Tweet;

public class PanelsCalls {
	
	// Abrir nueva ventana con timeline del usuario del tweet
	public static void openUserTimeline(Tweet tweet) {
		try {
			Stage stage = new Stage();
			stage.setTitle("TweDesk");
			stage.show();
			
			FXMLLoader layoutFXML = new FXMLLoader(TweDesk.class.getResource("/frames/UserMainFrame.fxml"));
			ControllerUserTimeline controllerUserTimeline = new ControllerUserTimeline(tweet.getUser().getScreen_name());
			layoutFXML.setController(controllerUserTimeline);
			Pane layout = layoutFXML.load();
			layout.getStyleClass().add("/frames/application.css");
			
			Scene scene = new Scene(layout);
			stage.setScene(scene);
		} catch (IOException e) { e.printStackTrace(); }
	}

	public static void openUserHome() {
		try {
			Stage stage = new Stage();
			stage.setTitle("TweDesk");
			stage.show();
			
			FXMLLoader layoutFXML = new FXMLLoader(TweDesk.class.getResource("/frames/UserMainFrame.fxml"));
			ControllerUserHome controllerUserHome = new ControllerUserHome(TweDesk.appUser.getUser_screen_name());
			layoutFXML.setController(controllerUserHome);
			Pane layout = layoutFXML.load();
			layout.getStyleClass().add("/frames/application.css");
			
			Scene scene = new Scene(layout);
			stage.setScene(scene);
		} catch(Exception e) { e.printStackTrace(); }
	}
	
}
