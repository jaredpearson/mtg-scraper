package mtgscraper.entities;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


public class Catalog {
	private List<Language> languages = null;
	
	public Catalog(List<Language> languages) {
		this.languages = languages;
	}
	
	public Language getLanguageByAbbr(String abbreviation) {
		for(Language language : languages) {
			if(language.getAbbr().equalsIgnoreCase(abbreviation)) {
				return language;
			}
		}
		throw new NoSuchElementException("No language found with abbreviation of " + abbreviation);
	}
	
	public CardSet getSetByAbbr(String languageAbbreviation, String setAbbreviation) {
		Language language = getLanguageByAbbr(languageAbbreviation);
		return language.getSetByAbbr(setAbbreviation);
	}
	
	public List<Language> getLanguages() {
		return Collections.unmodifiableList(this.languages);
	}
}