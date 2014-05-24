package mtgscraper.entities;

import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

/**
 * Represents a block of card sets. Each block contains multiple card sets however each card set only
 * belongs to one block.
 * @author jared.pearson
 */
public class Block {
	private @Nonnull final String name;
	private @Nonnull final List<? extends CardSetReference> cardSetReferences;
	
	public Block(@Nonnull final String name, @Nonnull List<? extends CardSetReference> cardSetReferences) {
		this.name = name;
		this.cardSetReferences = cardSetReferences;
	}
	
	/**
	 * Gets the name of the block.
	 */
	public @Nonnull String getName() {
		return name;
	}
	
	/**
	 * Gets the card set reference with the specified abbreviation.
	 * @param abbreviation the card set abbreviation
	 * @return the card set reference with the abbreviation
	 * @throws NoSuchElementException thrown when there is no card set with the given abbreviation.
	 */
	public @Nonnull CardSetReference getSetReferenceByAbbr(@Nonnull String abbreviation) {
		for (CardSetReference reference : cardSetReferences) {
			if (abbreviation.equalsIgnoreCase(reference.getAbbr())) {
				return reference;
			}
		}
		throw new NoSuchElementException("No card set found with the given abbreviation: " + abbreviation);
	}
	
	/**
	 * Gets a list of the card set references associated to this block
	 */
	public List<? extends CardSetReference> getSetReferences() {
		return cardSetReferences;
	}
}