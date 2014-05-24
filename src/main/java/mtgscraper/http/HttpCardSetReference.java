package mtgscraper.http;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mtgscraper.entities.CardSet;
import mtgscraper.entities.CardSetReference;

/**
 * Extension to the CardSetReference that stores the URL to retrieve the 
 * full information from.
 * @author jared.pearson
 */
public class HttpCardSetReference extends CardSetReference {
	private final String url;
	private final HttpCardSetProvider cardSetProvider;
	
	public HttpCardSetReference(
			@Nonnull final String url, 
			@Nullable final String name, 
			@Nullable final String abbr, 
			@Nullable String languageAbbr, 
			@Nonnull HttpCardSetProvider cardSetProvider) {
		super(abbr, name, languageAbbr);
		this.url = url;
		this.cardSetProvider = cardSetProvider;
	}
	
	public @Nonnull String getUrl() {
		return url;
	}
	
	/**
	 * Gets the card set associated to this reference
	 */
	public CardSet requestCardSet() {
		return cardSetProvider.requestCardSet(this);
	}
}