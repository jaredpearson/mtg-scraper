package mtgscraper.entities;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class Catalog {
	private @Nonnull final List<Language> languages;
	
	public Catalog(@Nonnull final List<Language> languages) {
		this.languages = languages;
	}
	
	public @Nonnull Language getLanguageByAbbr(@Nullable final String abbreviation) {
		for(Language language : languages) {
			if(language.getAbbr().equalsIgnoreCase(abbreviation)) {
				return language;
			}
		}
		throw new NoSuchElementException("No language found with abbreviation of " + abbreviation);
	}
	
	public @Nonnull CardSet getSetByAbbr(@Nullable final String languageAbbreviation, @Nonnull final String setAbbreviation) {
		Language language = getLanguageByAbbr(languageAbbreviation);
		return language.getSetByAbbr(setAbbreviation);
	}
	
	public @Nonnull List<Language> getLanguages() {
		return Collections.unmodifiableList(this.languages);
	}
}