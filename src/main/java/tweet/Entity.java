package tweet;

import media.Media;

public class Entity {
	
	Hashtag[] hashtags;
	Symbol[] symbols;
	UserMention[] user_mentions;
	Url[] urls;
	Media[] media;
	
	public Hashtag[] getHashtags() {
		return hashtags;
	}
	public void setHashtags(Hashtag[] hashtags) {
		this.hashtags = hashtags;
	}
	public Symbol[] getSymbols() {
		return symbols;
	}
	public void setSymbols(Symbol[] symbols) {
		this.symbols = symbols;
	}
	public UserMention[] getUser_mentions() {
		return user_mentions;
	}
	public void setUser_mentions(UserMention[] user_mentions) {
		this.user_mentions = user_mentions;
	}
	public Url[] getUrls() {
		return urls;
	}
	public void setUrls(Url[] urls) {
		this.urls = urls;
	}
	public Media[] getMedia() {
		return media;
	}
	public void setMedia(Media[] media) {
		this.media = media;
	}
	
}
