package mtgscraper.entities;

import java.util.List;
import java.util.NoSuchElementException;


public class Category {
	private String name;
	private List<Block> blocks = null;
	
	public Category(String name, List<Block> blocks) {
		this.name = name;
		this.blocks = blocks;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
	
	public CardSet getSetByAbbr(String abbreviation) {
		for(Block block : blocks) {
			try {
				return block.getSetByAbbr(abbreviation);
			} catch(NoSuchElementException exc) {
				
			}
		}
		throw new NoSuchElementException("No set found with abbreviation of " + abbreviation);
	}
}