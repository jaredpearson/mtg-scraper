package mtgscraper;

import java.util.List;

import javax.annotation.Nonnull;

import mtgscraper.entities.CardSet;
import mtgscraper.entities.CardSetReference;
import mtgscraper.entities.Catalog;

/**
 * Provider of card information
 * @author jared.pearson
 */
public interface DataProvider {

	/**
	 * Requests the catalog from the provider.
	 */
	public @Nonnull Catalog requestCatalog() throws Exception;
	
	/**
	 * Requests references for all of the card sets
	 */
	public @Nonnull List<? extends CardSetReference> requestCardSetReferences(@Nonnull String languageAbbr) throws Exception;
	
	/**
	 * Requests the card sets for the given language with the given abbreviations
	 * <p>
	 * All of the requested sets are returned or an exception is thrown
	 */
	public @Nonnull List<? extends CardSet> requestCardSetsByAbbrAllOrNone(@Nonnull String languageAbbr, @Nonnull String[] cardSetAbbrs) throws Exception;
	
	/**
	 * Requests the card set for the given reference
	 */
	public @Nonnull CardSet requestCardSet(@Nonnull CardSetReference cardSetReference) throws Exception; 
}
