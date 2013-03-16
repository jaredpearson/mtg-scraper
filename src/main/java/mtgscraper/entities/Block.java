package mtgscraper.entities;

public class Block {
	private CardSetProvider cardSetProvider;
	private String name;
	
	public Block(String name, CardSetProvider cardSetProvider) {
		this.name = name;
		this.cardSetProvider = cardSetProvider;
	}
	
	public String getName() {
		return name;
	}
	
	public CardSet getSetByAbbr(String abbreviation) {
		return cardSetProvider.getCardSetByAbbr(abbreviation);
	}
	
	public interface CardSetProvider {
		public CardSet getCardSetByAbbr(String abbreviation);
	}
}