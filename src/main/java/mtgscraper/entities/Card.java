package mtgscraper.entities;

import javax.annotation.Nullable;

public class Card {
	private @Nullable final String index;
	private @Nullable final String name;
	private @Nullable final String url;
	private @Nullable final String imageUrl;
	private @Nullable final String typeLine;
	private @Nullable final String castingCost;
	private @Nullable final String powerToughness;
	private @Nullable final String loyalty;
	private @Nullable final String rarity;
	private @Nullable final String artist;
	private @Nullable final String bodyText;
	
	public Card(@Nullable final String index, @Nullable final String name, @Nullable final String url, 
			@Nullable final String imageUrl, @Nullable final String typeLine, @Nullable final String castingCost, 
			@Nullable final String powerToughness, @Nullable final String loyalty, @Nullable final String rarity, 
			@Nullable final String artist, @Nullable final String bodyText) {
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
	
	public @Nullable String getIndex() {
		return index;
	}
	
	public @Nullable String getName() {
		return name;
	}
	
	public @Nullable String getUrl() {
		return url;
	}
	
	public @Nullable String getImageUrl() {
		return imageUrl;
	}
	
	public @Nullable String getTypeLine() {
		return typeLine;
	}
	
	public @Nullable String getCastingCost() {
		return castingCost;
	}
	
	public @Nullable String getPowerToughness() {
		return powerToughness;
	}
	
	public @Nullable String getLoyalty() {
		return loyalty;
	}
	
	public @Nullable String getRarity() {
		return rarity;
	}
	
	public @Nullable String getArtist() {
		return artist;
	}
	
	public @Nullable String getBodyText() {
		return bodyText;
	}
}