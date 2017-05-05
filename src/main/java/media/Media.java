package media;

import tweet.AditionalMediaInfo;

public class Media {
	
	long id;
	String id_str;
	int[] indices;
	
	String media_url;
	String media_url_https;
	String url;
	String display_url;
	String expanded_url;
	String type;
	
	MediaSize sizes;
	
	long source_status_id;
	String source_status_id_str;
	
	long source_user_id;
	String source_user_id_str;
	
	//Inluded in extended_entities
	VideoInfo video_info;
	AditionalMediaInfo aditional_media_info;
	
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
	public int[] getIndices() {
		return indices;
	}
	public void setIndices(int[] indices) {
		this.indices = indices;
	}
	public String getMedia_url() {
		return media_url;
	}
	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}
	public String getMedia_url_https() {
		return media_url_https;
	}
	public void setMedia_url_https(String media_url_https) {
		this.media_url_https = media_url_https;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDisplay_url() {
		return display_url;
	}
	public void setDisplay_url(String display_url) {
		this.display_url = display_url;
	}
	public String getExpanded_url() {
		return expanded_url;
	}
	public void setExpanded_url(String expanded_url) {
		this.expanded_url = expanded_url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MediaSize getSizes() {
		return sizes;
	}
	public void setSizes(MediaSize sizes) {
		this.sizes = sizes;
	}
	public long getSource_status_id() {
		return source_status_id;
	}
	public void setSource_status_id(long source_status_id) {
		this.source_status_id = source_status_id;
	}
	public String getSource_status_id_str() {
		return source_status_id_str;
	}
	public void setSource_status_id_str(String source_status_id_str) {
		this.source_status_id_str = source_status_id_str;
	}
	public long getSource_user_id() {
		return source_user_id;
	}
	public void setSource_user_id(long source_user_id) {
		this.source_user_id = source_user_id;
	}
	public String getSource_user_id_str() {
		return source_user_id_str;
	}
	public void setSource_user_id_str(String source_user_id_str) {
		this.source_user_id_str = source_user_id_str;
	}
	public VideoInfo getVideo_info() {
		return video_info;
	}
	public void setVideo_info(VideoInfo video_info) {
		this.video_info = video_info;
	}
	public AditionalMediaInfo getAditional_media_info() {
		return aditional_media_info;
	}
	public void setAditional_media_info(AditionalMediaInfo aditional_media_info) {
		this.aditional_media_info = aditional_media_info;
	}
	
}
