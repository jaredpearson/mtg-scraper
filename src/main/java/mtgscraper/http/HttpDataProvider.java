package mtgscraper.http;

import java.util.logging.Logger;

import mtgscraper.DataProvider;
import mtgscraper.entities.Catalog;

/**
 * Main class for the HTTP data provider.
 * @author jared.pearson
 */
public class HttpDataProvider implements DataProvider {
	private final Logger logger = Logger.getLogger(HttpDataProvider.class.getName());
	private Http http;
	private CardDownloadManager cardDownloadManager;
	private SiteMapProcessor siteMapProcessor;
	
	public HttpDataProvider() {
		this.http = new Http("http://magiccards.info");
		this.cardDownloadManager = new CardDownloadManager(http);
		
		HttpCardProviderFactory cardProviderFactory = new HttpCardProviderFactory(cardDownloadManager);
		CardSetProcessor cardSetProcessor = new CardSetProcessor(cardProviderFactory);
		HttpCardSetProviderFactory cardSetProviderFactory = new HttpCardSetProviderFactory(http, cardSetProcessor);
		this.siteMapProcessor = new SiteMapProcessor(cardSetProviderFactory);
	}
	
	/**
	 * Requests the catalog from the provider.
	 */
	@Override
	public Catalog requestCatalog() throws Exception {
		logger.fine("Downloading catalog");
		return http.requestDocument("/sitemap.html", siteMapProcessor);
	}
	
	public void shutdown() {
		if(cardDownloadManager != null) {
			cardDownloadManager.shutdown();
		}
	}

}
