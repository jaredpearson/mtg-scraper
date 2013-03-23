package mtgscraper;

import javax.annotation.Nonnull;

import mtgscraper.entities.Catalog;

public interface DataProvider {

	/**
	 * Requests the catalog from the provider.
	 */
	public @Nonnull Catalog requestCatalog() throws Exception;
}
