package mtgscraper.entities;

import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class Category {
	private final String name;
	private final List<Block> blocks;
	
	public Category(@Nullable final String name, @Nonnull final List<Block> blocks) {
		this.name = name;
		this.blocks = blocks;
	}
	
	public @Nullable String getName() {
		return name;
	}
	
	public @Nonnull List<Block> getBlocks() {
		return blocks;
	}
	
	public @Nonnull CardSet getSetByAbbr(@Nonnull String abbreviation) {
		for(Block block : blocks) {
			try {
				return block.getSetByAbbr(abbreviation);
			} catch(NoSuchElementException exc) {
				
			}
		}
		throw new NoSuchElementException("No set found with abbreviation of " + abbreviation);
	}
}