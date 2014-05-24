package mtgscraper.entities;

import javax.annotation.Nullable;

/**
 * Similar to an ID, this reference is used by the service layer to retrieve the full card set
 * 
 * @author jared.pearson
 */
public abstract class CardSetReference {
	private final String abbr;
	private final String name;
	private final String languageAbbr;
	
	public CardSetReference(String abbr, String name, String languageAbbr) {
		this.name = name;
		this.abbr = abbr;
		this.languageAbbr = languageAbbr;
	}
	
	public @Nullable String getAbbr() {
		return abbr;
	}
	
	public @Nullable String getName() {
		return name;
	}

	public @Nullable String getLanguageAbbr() {
		return languageAbbr;
	}
}
