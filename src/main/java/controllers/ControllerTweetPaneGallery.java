package controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import tweet.Tweet;

public class ControllerTweetPaneGallery extends ControllerTweetPaneImage {
	
	@FXML Button buttonPrevious;
	@FXML Button buttonNext;
	
	private int imageIndex = 0;
	
	public ControllerTweetPaneGallery(Tweet tweet) {
		super(tweet);
	}

	@Override
	public void populateTweetPanel() {
		super.populateTweetPanel();

		media.Media tweetMediaInfo = tweet.getExtended_entities().getMedia()[0];
		imageView.setFitHeight(tweetMediaInfo.getSizes().getLarge().getH()*190.0/tweetMediaInfo.getSizes().getLarge().getW());
		
		buttonPrevious.setPrefHeight(imageView.getFitHeight());
		buttonPrevious.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				changeImageShownPrevious();
			}
		});
		
		buttonNext.setPrefHeight(imageView.getFitHeight());
		buttonNext.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				changeImageShownNext();
			}
		});
	}
	
	protected void changeImageShownPrevious() {
		if(imageIndex == 0) imageIndex = tweet.getEntities().getMedia().length;
		else imageIndex--;
		
		Image image = new Image(tweet.getExtended_entities().getMedia()[imageIndex].getMedia_url(), true);
		imageView.setImage(image);
	}

	protected void changeImageShownNext() {
		if(imageIndex == tweet.getEntities().getMedia().length) imageIndex = 0;
		else imageIndex++;
		
		Image image = new Image(tweet.getExtended_entities().getMedia()[imageIndex].getMedia_url(), true);
		imageView.setImage(image);
	}

}
