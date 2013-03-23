package mtgscraper.http;

import java.util.List;

import javax.annotation.Nonnull;

public class HttpCardSetProviderFactory {
	private @Nonnull final Http http;
	private @Nonnull final CardSetProcessor cardSetProcessor;
	
	public HttpCardSetProviderFactory(@Nonnull final Http http, @Nonnull final CardSetProcessor cardSetProcessor) {
		this.http = http;
		this.cardSetProcessor = cardSetProcessor;
	}
	
	/**
	 * Creates a new HTTP CardSet provider for the given card sets.
	 */
	public @Nonnull HttpCardSetProvider getForSets(@Nonnull List<CardSetLink> sets) {
		return new HttpCardSetProvider(http, cardSetProcessor, sets);
	}
}
