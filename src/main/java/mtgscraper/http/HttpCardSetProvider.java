package mtgscraper.http;

import java.io.IOException;

import javax.annotation.Nonnull;

import mtgscraper.entities.CardSet;

/**
 * Provides the card set using an HTTP connect to request the card set information.
 * @author jared.pearson
 */
public class HttpCardSetProvider {
	private @Nonnull final Http http;
	private @Nonnull final CardSetProcessor cardSetProcessor;
	
	public HttpCardSetProvider(@Nonnull final Http http, @Nonnull final CardSetProcessor cardSetProcessor) {
		assert http != null;
		assert cardSetProcessor != null;
		this.http = http;
		this.cardSetProcessor = cardSetProcessor;
	}
	
	/**
	 * Requests the card set at the given set link.
	 */
	public @Nonnull CardSet requestCardSet(@Nonnull final HttpCardSetReference set) {
		assert set != null;
		try {
			return http.requestDocument(set.getUrl(), cardSetProcessor);
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}
	
}