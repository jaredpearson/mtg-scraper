package mtgscraper.http;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CardSetLink {
	private final String name;
	private final String abbr;
	private final String url;
	
	public CardSetLink(@Nonnull final String url, @Nullable final String name, @Nullable final String abbr) {
		this.name = name;
		this.abbr = abbr;
		this.url = url;
	}
	
	public @Nullable String getName() {
		return name;
	}
	
	public @Nullable String getAbbr() {
		return abbr;
	}
	
	public @Nonnull String getUrl() {
		return url;
	}
}