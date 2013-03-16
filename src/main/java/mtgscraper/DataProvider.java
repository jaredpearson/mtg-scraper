package mtgscraper;

import mtgscraper.entities.Catalog;

public interface DataProvider {

	/**
	 * Requests the catalog from the provider.
	 */
	public Catalog requestCatalog() throws Exception;
}
