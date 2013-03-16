package mtgscraper.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


public class Language {
	private String name;
	private String abbr;
	private List<Category> categories = null;
	
	public Language(String name, String abbr, List<Category> categories) {
		this.name = name;
		this.abbr = abbr;
		this.categories = categories;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAbbr() {
		return abbr;
	}
	
	public CardSet getSetByAbbr(String abbreviation) {
		for(Category category : categories) {
			try {
				return category.getSetByAbbr(abbreviation);
			} catch(NoSuchElementException exc) {
				
			}
		}
		throw new NoSuchElementException("No set found with abbreviation of " + abbreviation);
	}
	
	public List<Block> getBlocks() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(Category category : categories) {
			blocks.addAll(category.getBlocks());
		}
		return Collections.unmodifiableList(blocks);
	}
}