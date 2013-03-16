package mtgscraper.http;

public class CardLink {
	private String url;
	private String typeLine;
	private String powerToughness;
	private String loyalty;
	private String castingCost;
	private String rarity;
	private String artist;
	
	public CardLink(String url, String typeLine, String powerToughness, String loyalty, String castingCost, String rarity, String artist) {
		this.url = url;
		this.typeLine = typeLine;
		this.powerToughness = powerToughness;
		this.loyalty = loyalty;
		this.castingCost = castingCost;
		this.rarity = rarity;
		this.artist = artist;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTypeLine() {
		return typeLine;
	}
	
	public String getPowerToughness() {
		return powerToughness;
	}
	
	public String getLoyalty() {
		return loyalty;
	}
	
	public String getRarity() {
		return rarity;
	}
	
	public String getCastingCost() {
		return castingCost;
	}
	
	public String getArtist() {
		return artist;
	}
}