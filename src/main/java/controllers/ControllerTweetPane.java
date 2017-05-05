package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import karlamsoft.PanelsCalls;
import karlamsoft.Utils;
import tweet.Tweet;

public class ControllerTweetPane implements Initializable {
	
	Tweet tweet;
	
	@FXML Pane tweetPane;
	@FXML ImageView userImage;
	@FXML Label userScreenName;
	@FXML Label labelDate;
	@FXML TextArea tweetBody;
	
	public ControllerTweetPane(Tweet tweet) {
		this.tweet = tweet;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateTweetPanel();
		userImage.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				PanelsCalls.openUserTimeline(tweet);
			}
		});
		userScreenName.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				PanelsCalls.openUserTimeline(tweet);
			}
		});
	}

	public void populateTweetPanel() {
		userImage.setImage(new Image(tweet.getUser().getProfile_image_url(), true));
		userScreenName.setText(tweet.getUser().getScreen_name());
		labelDate.setText(Utils.tweetDateFormat(tweet.getCreated_at()));
		tweetBody.setText(tweet.getText());
		
		//tweet = null;
	}
	
}
