package mtgscraper.http;

import javax.annotation.Nonnull;

/**
 * Factory for creating card set providers
 * @author jared.pearson
 */
class HttpCardSetProviderFactory {
	private @Nonnull final Http http;
	private @Nonnull final CardSetProcessor cardSetProcessor;
	
	public HttpCardSetProviderFactory(@Nonnull final Http http, @Nonnull final CardSetProcessor cardSetProcessor) {
		this.http = http;
		this.cardSetProcessor = cardSetProcessor;
	}
	
	/**
	 * Creates a new HTTP CardSet provider.
	 */
	public @Nonnull HttpCardSetProvider create() {
		return new HttpCardSetProvider(http, cardSetProcessor);
	}
}
