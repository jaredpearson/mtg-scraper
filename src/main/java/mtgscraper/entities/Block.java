package mtgscraper.entities;

import javax.annotation.Nonnull;

public class Block {
	private @Nonnull final CardSetProvider cardSetProvider;
	private @Nonnull final String name;
	
	public Block(@Nonnull final String name, @Nonnull final CardSetProvider cardSetProvider) {
		this.name = name;
		this.cardSetProvider = cardSetProvider;
	}
	
	public @Nonnull String getName() {
		return name;
	}
	
	public @Nonnull CardSet getSetByAbbr(@Nonnull String abbreviation) {
		return cardSetProvider.getCardSetByAbbr(abbreviation);
	}
	
	public interface CardSetProvider {
		public @Nonnull CardSet getCardSetByAbbr(@Nonnull String abbreviation);
	}
}