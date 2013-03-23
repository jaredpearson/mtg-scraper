package mtgscraper.entities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mtgscraper.Visitor;

public class CardSet {
	private @Nullable final String name;
	private @Nullable final String abbr;
	private @Nonnull final CardProvider cardProvider;
	private @Nullable String language;

	public CardSet(@Nullable final String name, @Nullable final String abbr, @Nullable final String language, 
			@Nonnull final CardProvider cardProvider) {
		this.name = name;
		this.abbr = abbr;
		this.language = language;
		this.cardProvider = cardProvider;
	}
	
	public @Nullable String getName() {
		return name;
	}
	
	public @Nullable String getAbbr() {
		return abbr;
	}
	
	public @Nullable String getLanguage() {
		return language;
	}
	
	public int getTotalNumberOfCardsInSet() {
		return cardProvider.getTotal();
	}
	
	/**
	 * Iterates over each card in the set.
	 */
	public void eachCard(@Nonnull Visitor<Card> visitor) {
		cardProvider.eachCard(visitor);
	}
	
	public interface CardProvider {
		/**
		 * Gets the total number of cards
		 */
		public int getTotal();
		public void eachCard(@Nonnull Visitor<Card> visitor);
	}
}