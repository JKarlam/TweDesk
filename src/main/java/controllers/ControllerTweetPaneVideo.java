package controllers;

import java.awt.Toolkit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import tweet.Tweet;

public class ControllerTweetPaneVideo extends ControllerTweetPane {
	
	@FXML MediaView mediaView;
	@FXML Label gifLabel;
	@FXML AnchorPane endAnchor;
	
	public ControllerTweetPaneVideo(Tweet tweet) {
		super(tweet);
	}
	
	@Override
	public void populateTweetPanel() {
		super.populateTweetPanel();
		
		media.Media tweetMediaInfo = tweet.getExtended_entities().getMedia()[0];
		Media video = new Media(tweetMediaInfo.getVideo_info().getVariants()[0].getUrl());
		MediaPlayer player = new MediaPlayer(video);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		mediaView.setFitHeight(tweetMediaInfo.getSizes().getLarge().getH()*230.0/tweetMediaInfo.getSizes().getLarge().getW());
		mediaView.setMediaPlayer(player);
		gifLabel.setLayoutY(mediaView.getBoundsInParent().getMaxY());
		endAnchor.setLayoutY(gifLabel.getLayoutY()+gifLabel.getHeight());

		mediaView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				openVideo(mediaView.getMediaPlayer().getMedia());
			}
		});
		mediaView.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				mediaView.getMediaPlayer().play();
			}
		});
		mediaView.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				mediaView.getMediaPlayer().stop();
			}
		});
	}
	
	private void openVideo(Media video) {
		final Stage stage = new Stage();
		Pane layout = new Pane();
		final Scene scene = new Scene(layout);
		stage.setScene(scene);
		
		MediaPlayer player = new MediaPlayer(video);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		final MediaView mediaView = new MediaView(player);
		mediaView.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);
		mediaView.setCursor(Cursor.HAND);
		mediaView.setPreserveRatio(true);
		mediaView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				mediaView.getMediaPlayer().stop();
				mediaView.setMediaPlayer(null);
				stage.close();
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				mediaView.setFitHeight(newValue.doubleValue());
				mediaView.setLayoutX(scene.getWidth()/2-mediaView.getBoundsInParent().getWidth()/2);
			}
		});
		layout.getChildren().add(mediaView);
		
		stage.show();
		player.play();
	}
}
