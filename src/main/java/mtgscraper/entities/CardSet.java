package mtgscraper.entities;

import mtgscraper.Visitor;

public class CardSet {
	private String name;
	private String abbr;
	private CardProvider cardProvider;
	private String language;

	public CardSet(String name, String abbr, String language, CardProvider cardProvider) {
		this.name = name;
		this.abbr = abbr;
		this.language = language;
		this.cardProvider = cardProvider;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAbbr() {
		return abbr;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public int getTotalNumberOfCardsInSet() {
		return cardProvider.getTotal();
	}
	
	/**
	 * Iterates over each card in the set.
	 */
	public void eachCard(Visitor<Card> visitor) {
		cardProvider.eachCard(visitor);
	}
	
	public interface CardProvider {
		/**
		 * Gets the total number of cards
		 */
		public int getTotal();
		public void eachCard(Visitor<Card> visitor);
	}
}