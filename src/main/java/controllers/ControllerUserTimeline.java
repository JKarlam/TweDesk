package controllers;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import karlamsoft.TwitterCalls;
import tweet.Tweet;

public class ControllerUserTimeline extends ControllerUserHome {
		
	public ControllerUserTimeline(String screen_name) {
		super(screen_name);
	}

	@Override	
	public void populateTweetsPane() {
		// Limpiar panel y lista de tweets
		tweetPaneList.clear();
		paneTweets.getChildren().clear();
		
		Gson gson = new Gson();
		
		// Obtener tweets
		String jsonTweets = TwitterCalls.getUserTweets(userInfo.getScreen_name(), Integer.valueOf(tweetLoads.getValue()));
		JsonElement jsonElement = new JsonParser().parse(jsonTweets);
		JsonArray array = jsonElement.getAsJsonArray();
		
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
			Tweet tweet = gson.fromJson(array.get(i), Tweet.class);
			
			try {
				FXMLLoader tweetPaneFXML = null;
				ControllerTweetPane controllerTweetPane = null;
				
				if(tweet.getExtended_entities() != null && tweet.getExtended_entities().getMedia()[0].getVideo_info() != null) { // Vídeo
					tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPaneVideo.fxml"));
					controllerTweetPane = new ControllerTweetPaneVideo(tweet);
				} else if(tweet.getEntities().getMedia() != null) { // Una imagen
					if(tweet.getEntities().getMedia().length == 1) {
						tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPaneImage.fxml"));
						controllerTweetPane = new ControllerTweetPaneImage(tweet);
					} else if(tweet.getEntities().getMedia().length > 1) { // Galería
						tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPaneGallery.fxml"));
						controllerTweetPane = new ControllerTweetPaneGallery(tweet);
					}
				} else { // Solo texto
					tweetPaneFXML = new FXMLLoader(getClass().getResource("/frames/TweetPane.fxml"));
					controllerTweetPane = new ControllerTweetPane(tweet);
				}
				
				tweetPaneFXML.setController(controllerTweetPane);
				Pane tweetPane = tweetPaneFXML.load();
				tweetPaneList.add(tweetPane);
				
				paneTweets.getChildren().add(tweetPaneList.get(i));
				
				tweetPaneList.get(i).setLayoutX((i%4)*250);
				if(i>3)tweetPaneList.get(i).setLayoutY(tweetPaneList.get(i-4).getBoundsInParent().getMaxY());
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		tweetPaneList.clear();
	}
	
}
