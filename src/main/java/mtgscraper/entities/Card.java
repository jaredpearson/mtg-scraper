package mtgscraper.entities;

public class Card {
	private String index;
	private String name;
	private String url;
	private String imageUrl;
	private String typeLine;
	private String castingCost;
	private String powerToughness;
	private String loyalty;
	private String rarity;
	private String artist;
	private String bodyText;
	
	public Card(String index, String name, String url, String imageUrl, String typeLine, String castingCost, String powerToughness, String loyalty, String rarity, String artist, String bodyText) {
		this.index = index;
		this.name = name;
		this.url = url;
		this.imageUrl = imageUrl;
		this.typeLine = typeLine;
		this.castingCost = castingCost;
		this.powerToughness = powerToughness;
		this.loyalty = loyalty;
		this.rarity = rarity;
		this.artist = artist;
		this.bodyText = bodyText;
	}
	
	public String getIndex() {
		return index;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getTypeLine() {
		return typeLine;
	}
	
	public String getCastingCost() {
		return castingCost;
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
	
	public String getArtist() {
		return artist;
	}
	
	public String getBodyText() {
		return bodyText;
	}
}