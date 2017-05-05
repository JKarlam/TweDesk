package tweet;

public class Tweet {
	
	String created_at; //Formatted date
	long id; //message ID
	String id_str; //IDs are in long and in string format
	String text; //content
	Entity entities;
	Entity extended_entities;
	String source;
	
	String in_reply_to_status_id;
    String in_reply_to_status_id_str;
    String in_reply_to_user_id;
    String in_reply_to_user_id_str;
    String in_reply_to_screen_name;
    
    User user;
    
    String geo;
    String coordinates;
    Place place;
    String contributors;
    
    //Retweet info if last tweet is a retweet
    Tweet retweeted_status;
    
    //Quoted tweet if any
    long quoted_status_id;
    String quoted_status_id_str;
    Tweet quoted_status;
    
    boolean is_quote_status;
    int retweet_count;
    int favorite_count;
    boolean favorited;
    boolean retweeted;
    boolean possible_sensitive;
    String lang;
    
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getId_str() {
		return id_str;
	}
	public void setId_str(String id_str) {
		this.id_str = id_str;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Entity getEntities() {
		return entities;
	}
	public void setEntities(Entity entities) {
		this.entities = entities;
	}
	public Entity getExtended_entities() {
		return extended_entities;
	}
	public void setExtended_entities(Entity extended_entities) {
		this.extended_entities = extended_entities;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}
	public void setIn_reply_to_status_id(String in_reply_to_status_id) {
		this.in_reply_to_status_id = in_reply_to_status_id;
	}
	public String getIn_reply_to_status_id_str() {
		return in_reply_to_status_id_str;
	}
	public void setIn_reply_to_status_id_str(String in_reply_to_status_id_str) {
		this.in_reply_to_status_id_str = in_reply_to_status_id_str;
	}
	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}
	public void setIn_reply_to_user_id(String in_reply_to_user_id) {
		this.in_reply_to_user_id = in_reply_to_user_id;
	}
	public String getIn_reply_to_user_id_str() {
		return in_reply_to_user_id_str;
	}
	public void setIn_reply_to_user_id_str(String in_reply_to_user_id_str) {
		this.in_reply_to_user_id_str = in_reply_to_user_id_str;
	}
	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}
	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public String getContributors() {
		return contributors;
	}
	public void setContributors(String contributors) {
		this.contributors = contributors;
	}
	public Tweet getRetweeted_status() {
		return retweeted_status;
	}
	public void setRetweeted_status(Tweet retweeted_status) {
		this.retweeted_status = retweeted_status;
	}
	public long getQuoted_status_id() {
		return quoted_status_id;
	}
	public void setQuoted_status_id(long quoted_status_id) {
		this.quoted_status_id = quoted_status_id;
	}
	public String getQuoted_status_id_str() {
		return quoted_status_id_str;
	}
	public void setQuoted_status_id_str(String quoted_status_id_str) {
		this.quoted_status_id_str = quoted_status_id_str;
	}
	public Tweet getQuoted_status() {
		return quoted_status;
	}
	public void setQuoted_status(Tweet quoted_status) {
		this.quoted_status = quoted_status;
	}
	public boolean isIs_quote_status() {
		return is_quote_status;
	}
	public void setIs_quote_status(boolean is_quote_status) {
		this.is_quote_status = is_quote_status;
	}
	public int getRetweet_count() {
		return retweet_count;
	}
	public void setRetweet_count(int retweet_count) {
		this.retweet_count = retweet_count;
	}
	public int getFavorite_count() {
		return favorite_count;
	}
	public void setFavorite_count(int favorite_count) {
		this.favorite_count = favorite_count;
	}
	public boolean isFavorited() {
		return favorited;
	}
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	public boolean isRetweeted() {
		return retweeted;
	}
	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}
	public boolean isPossible_sensitive() {
		return possible_sensitive;
	}
	public void setPossible_sensitive(boolean possible_sensitive) {
		this.possible_sensitive = possible_sensitive;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
}
