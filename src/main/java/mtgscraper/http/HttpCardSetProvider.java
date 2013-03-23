package mtgscraper.http;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mtgscraper.entities.CardSet;
import mtgscraper.entities.Block.CardSetProvider;

public class HttpCardSetProvider implements CardSetProvider {
	private @Nonnull final Http http;
	private @Nonnull final CardSetProcessor cardSetProcessor;
	private @Nonnull final List<CardSetLink> sets;
	
	public HttpCardSetProvider(@Nonnull final Http http, @Nonnull final CardSetProcessor cardSetProcessor, 
			@Nonnull final List<CardSetLink> sets) {
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
	private @Nonnull CardSet requestCardSet(@Nonnull final CardSetLink set) {
		try {
			return http.requestDocument(set.getUrl(), cardSetProcessor);
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}
	
	/**
	 * Gets the set link with the given abbreviation.
	 */
	private @Nonnull CardSetLink getSetLinkByAbbr(@Nullable final String abbreviation) {
		for(CardSetLink set : sets) {
			if(set.getAbbr().equalsIgnoreCase(abbreviation)) {
				return set;
			}
		}
		throw new NoSuchElementException("No set found with abbreviation of " + abbreviation);
	}
}