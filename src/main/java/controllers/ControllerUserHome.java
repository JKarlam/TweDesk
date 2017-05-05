package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import karlamsoft.TweDesk;
import karlamsoft.TwitterCalls;
import tweet.Tweet;
import tweet.User;

public class ControllerUserHome implements Initializable {
	
	User userInfo;
	
	String screen_name;
	ArrayList<Pane> tweetPaneList = new ArrayList<>();
	
	@FXML private ImageView imageUserIcon;
	@FXML private Label labelScreenName;
	@FXML protected ComboBox<String> tweetLoads;
	
	@FXML protected AnchorPane paneTweets;
	
	public ControllerUserHome(String screen_name) {
		this.screen_name = screen_name;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Gson gson = new Gson();
		
		// Con la autorización pedir la información de cuenta del usuario
		String jsonUser = TwitterCalls.getUserInfo(screen_name);
		userInfo = gson.fromJson(jsonUser, User.class);
		
		tweetLoads.getItems().addAll("20", "50", "100", "200");
		tweetLoads.setValue(TweDesk.appUser.getShow_tweets_number());
		tweetLoads.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				TweDesk.appUser.setShow_tweets_number(tweetLoads.getValue());
				populateTweetsPane();
				for (int i = 0; i < tweetPaneList.size(); i++) {
					tweetPaneList.get(i).setLayoutX((i%4)*250);
					if(i>3)tweetPaneList.get(i).setLayoutY(tweetPaneList.get(i-4).getBoundsInParent().getMaxY());
				}
				tweetPaneList.clear();
			}
		});

		populateUserPane();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				populateTweetsPane();
				for (int i = 0; i < tweetPaneList.size(); i++) {
					tweetPaneList.get(i).setLayoutX((i%4)*250);
					if(i>3)tweetPaneList.get(i).setLayoutY(tweetPaneList.get(i-4).getBoundsInParent().getMaxY());
				}
				tweetPaneList.clear();
			}
		});
	}

	private void populateUserPane() {
		imageUserIcon.setImage(new Image(userInfo.getProfile_image_url(), true));
		labelScreenName.setText(userInfo.getScreen_name());
	}
	
	public void populateTweetsPane() {
		// Limpiar panel y lista de tweets
		tweetPaneList.clear();
		paneTweets.getChildren().clear();
		
		Gson gson = new Gson();
		
		// Obtener tweets
		String jsonTweets = TwitterCalls.getHomeTweets(Integer.valueOf(tweetLoads.getValue()));
		JsonElement jsonElement = new JsonParser().parse(jsonTweets);
		JsonArray array = jsonElement.getAsJsonArray();
		
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
			Tweet tweet = gson.fromJson(array.get(i), Tweet.class);
			
			try {
				FXMLLoader tweetPaneFXML = null;
				ControllerTweetPane controllerTweetPane = null;
				
				if(tweet.getExtended_entities() != null) { //Tweets con solo texto no tienen un "extended_entities"
					if(tweet.getExtended_entities().getMedia()[0].getVideo_info() != null) { // Vídeo
						tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPaneVideo.fxml"));
						controllerTweetPane = new ControllerTweetPaneVideo(tweet);
						System.out.println(" - Gif");
					} else if(tweet.getExtended_entities().getMedia().length == 1) { // Una imagen
						tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPaneImage.fxml"));
						controllerTweetPane = new ControllerTweetPaneImage(tweet);
					} else if(tweet.getExtended_entities().getMedia().length > 1) { // Galería
						tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPaneGallery.fxml"));
						controllerTweetPane = new ControllerTweetPaneGallery(tweet);
						System.out.println(" - Gallery");
					}
				} else { // Solo texto
					tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPane.fxml"));
					controllerTweetPane = new ControllerTweetPane(tweet);
				}
				
				tweetPaneFXML.setController(controllerTweetPane);
				Pane tweetPane = tweetPaneFXML.load();
				tweetPaneList.add(tweetPane);
				
				paneTweets.getChildren().add(tweetPaneList.get(i));
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
}
