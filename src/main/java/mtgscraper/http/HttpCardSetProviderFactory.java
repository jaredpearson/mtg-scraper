package mtgscraper.http;

import java.util.List;

public class HttpCardSetProviderFactory {
	private Http http;
	private CardSetProcessor cardSetProcessor;
	
	public HttpCardSetProviderFactory(Http http, CardSetProcessor cardSetProcessor) {
		this.http = http;
		this.cardSetProcessor = cardSetProcessor;
	}
	
	/**
	 * Creates a new HTTP CardSet provider for the given card sets.
	 */
	public HttpCardSetProvider getForSets(List<CardSetLink> sets) {
		return new HttpCardSetProvider(http, cardSetProcessor, sets);
	}
}
