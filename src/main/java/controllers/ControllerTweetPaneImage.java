package controllers;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tweet.Tweet;

public class ControllerTweetPaneImage extends ControllerTweetPane {
	
	@FXML ImageView imageView;
	
	public ControllerTweetPaneImage(Tweet tweet) {
		super(tweet);
	}
	
	@Override
	public void populateTweetPanel() {
		super.populateTweetPanel();
		
		media.Media tweetMediaInfo = tweet.getExtended_entities().getMedia()[0];
		File imageFile = new File("./cache/"+tweetMediaInfo.getId());
		if(imageFile.exists()) imageView.setImage(new Image(imageFile.toURI().toString(), true)); 
		else {
			Image image = new Image(tweetMediaInfo.getMedia_url(), true);
			imageView.setImage(image);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(image.getProgress() < 1) 
							synchronized (this) {
								wait(5000);
							}
						saveImage(image, imageFile);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		imageView.setFitHeight(tweetMediaInfo.getSizes().getLarge().getH()*230.0/tweetMediaInfo.getSizes().getLarge().getW());
		
		imageView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				openImage();
			}
		});
	}
	
	private void saveImage(Image image, File output) throws IOException {
		BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);
		ImageIO.write(bimg, "jpg", output);
	}

	private void openImage() {
		final Stage stage = new Stage();
		Pane layout = new Pane();
		final Scene scene = new Scene(layout);
		stage.setScene(scene);
		
		final ImageView imageView2 = new ImageView(imageView.getImage());
		imageView2.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);
		imageView2.setLayoutX(scene.getWidth()/2-imageView2.getImage().getWidth()/2);
		imageView2.setCursor(Cursor.HAND);
		imageView2.setPreserveRatio(true);
		imageView2.setSmooth(true);
		imageView2.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				imageView2.setImage(null);
				stage.close();
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				imageView2.setFitHeight(newValue.doubleValue());
				imageView2.setLayoutX(scene.getWidth()/2-imageView2.getBoundsInParent().getWidth()/2);
			}
		});
		layout.getChildren().add(imageView2);
		
		stage.show();
	}
}
