package controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import karlamsoft.AppUser;
import karlamsoft.PanelsCalls;
import karlamsoft.TweDesk;
import karlamsoft.Utils;

public class ControllerPinPane implements Initializable {

	private Stage stage;
	
	@FXML TextField pinNumber;
	@FXML Button buttonOk;
	
	public ControllerPinPane(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Cargar autorización del usuario desde archivo o pedirla a Twitter mediante pin y guardar el archivo
		// La autorización deberia pedirse solo la primera vez que se inicia la aplicación
		if((TweDesk.appUser = Utils.loadUserConfig()) == null) {
			TweDesk.appUser = new AppUser();
			TweDesk.appUser.setShow_tweets_number("20");
			TweDesk.appUser.requestOAuthToken();
			
			try {
				Desktop.getDesktop().browse(new URI("https://api.twitter.com/oauth/authenticate?oauth_token="+TweDesk.appUser.getOAUTH_TOKEN()+"&oauth_callback=oob"));
			} catch (IOException e) { 
				e.printStackTrace(); 
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			buttonOk.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					TweDesk.appUser.requestAccessToken(pinNumber.getText());
					TweDesk.appUser.saveUserConfig();
					PanelsCalls.openUserHome();
					stage.hide();
				};
			});
			
			// Mostrar la ventana solo si hace falta
			stage.show();
		} else PanelsCalls.openUserHome();
	}
	
}
