package tweet;

public class Place {
	
	String id;
	String url;
	String place_type;
	String name;
	String full_name;
	String country_code;
	String country;
	
	int[] contained_within;
	BoundingBox bounding_box;
	PlaceAttributes attributes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPlace_type() {
		return place_type;
	}
	public void setPlace_type(String place_type) {
		this.place_type = place_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int[] getContained_within() {
		return contained_within;
	}
	public void setContained_within(int[] contained_within) {
		this.contained_within = contained_within;
	}
	public BoundingBox getBounding_box() {
		return bounding_box;
	}
	public void setBounding_box(BoundingBox bounding_box) {
		this.bounding_box = bounding_box;
	}
	public PlaceAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(PlaceAttributes attributes) {
		this.attributes = attributes;
	}
	
}
