package mtgscraper.http;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CardLink {
	private @Nonnull final String url;
	private final String typeLine;
	private final String powerToughness;
	private final String loyalty;
	private final String castingCost;
	private final String rarity;
	private final String artist;
	
	public CardLink(@Nonnull final String url, @Nullable final String typeLine, @Nullable final String powerToughness, 
			@Nullable final String loyalty, @Nullable final String castingCost, @Nullable final String rarity, 
			@Nullable final String artist) {
		this.url = url;
		this.typeLine = typeLine;
		this.powerToughness = powerToughness;
		this.loyalty = loyalty;
		this.castingCost = castingCost;
		this.rarity = rarity;
		this.artist = artist;
	}
	
	public @Nonnull String getUrl() {
		return url;
	}
	
	public @Nullable String getTypeLine() {
		return typeLine;
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
	
	public @Nullable String getCastingCost() {
		return castingCost;
	}
	
	public @Nullable String getArtist() {
		return artist;
	}
}