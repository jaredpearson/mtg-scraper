package mtgscraper.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The language in which the cards are printed.
 * @author jared.pearson
 */
public class Language {
	private @Nullable final String name;
	private @Nullable final String abbr;
	private @Nonnull final List<Category> categories;
	
	public Language(@Nullable final String name, @Nullable final String abbr, @Nonnull final List<Category> categories) {
		this.name = name;
		this.abbr = abbr;
		this.categories = categories;
	}
	
	/**
	 * Gets the human readable name for the language
	 */
	public @Nullable String getName() {
		return name;
	}
	
	/**
	 * Gets the 2-char abbreviation for the language
	 */
	public @Nullable String getAbbr() {
		return abbr;
	}
	
	public @Nonnull CardSetReference getSetReferenceByAbbr(@Nonnull String abbreviation) {
		for(Category category : categories) {
			try {
				return category.getSetReferenceByAbbr(abbreviation);
			} catch(NoSuchElementException exc) {
				
			}
		}
		throw new NoSuchElementException("No set found with abbreviation of " + abbreviation);
	}
	
	public @Nonnull List<Block> getBlocks() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(Category category : categories) {
			blocks.addAll(category.getBlocks());
		}
		return Collections.unmodifiableList(blocks);
	}
}