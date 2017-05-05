package media;

public class VideoInfo {
	
	int[] aspect_ratio;
	int duration_milis;
	MediaVariant[] variants;
	
	public int[] getAspect_ratio() {
		return aspect_ratio;
	}
	public void setAspect_ratio(int[] aspect_ratio) {
		this.aspect_ratio = aspect_ratio;
	}
	public int getDuration_milis() {
		return duration_milis;
	}
	public void setDuration_milis(int duration_milis) {
		this.duration_milis = duration_milis;
	}
	public MediaVariant[] getVariants() {
		return variants;
	}
	public void setVariants(MediaVariant[] variants) {
		this.variants = variants;
	}
	
}
