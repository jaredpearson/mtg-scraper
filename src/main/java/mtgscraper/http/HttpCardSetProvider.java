package mtgscraper.http;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import mtgscraper.entities.CardSet;
import mtgscraper.entities.Block.CardSetProvider;

public class HttpCardSetProvider implements CardSetProvider {
	private Http http;
	private CardSetProcessor cardSetProcessor;
	private List<CardSetLink> sets = null;
	
	public HttpCardSetProvider(Http http, CardSetProcessor cardSetProcessor, List<CardSetLink> sets) {
		this.http = http;
		this.cardSetProcessor = cardSetProcessor;
		this.sets = sets;
	}
	
	@Override
	public CardSet getCardSetByAbbr(String abbreviation) {
		CardSetLink setLink = getSetLinkByAbbr(abbreviation);
		return requestCardSet(setLink);
	}
	
	/**
	 * Requests the card set at the given set link.
	 */
	private CardSet requestCardSet(CardSetLink set) {
		try {
			return http.requestDocument(set.getUrl(), cardSetProcessor);
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}
	
	/**
	 * Gets the set link with the given abbreviation.
	 */
	private CardSetLink getSetLinkByAbbr(String abbreviation) {
		for(CardSetLink set : sets) {
			if(set.getAbbr().equalsIgnoreCase(abbreviation)) {
				return set;
			}
		}
		throw new NoSuchElementException("No set found with abbreviation of " + abbreviation);
	}
}