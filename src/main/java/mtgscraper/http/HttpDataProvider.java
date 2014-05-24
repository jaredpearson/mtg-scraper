package mtgscraper.http;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import mtgscraper.DataProvider;
import mtgscraper.entities.Block;
import mtgscraper.entities.CardSet;
import mtgscraper.entities.CardSetReference;
import mtgscraper.entities.Catalog;
import mtgscraper.entities.Language;

/**
 * Main class for the HTTP data provider.
 * @author jared.pearson
 */
public class HttpDataProvider implements DataProvider {
	private final Logger logger = Logger.getLogger(HttpDataProvider.class.getName());
	private @Nonnull final Http http;
	private @Nonnull final CardDownloadManager cardDownloadManager;
	private @Nonnull final SiteMapProcessor siteMapProcessor;
	
	public HttpDataProvider() {
		this.http = new Http("http://magiccards.info");
		this.cardDownloadManager = new CardDownloadManager(http);
		
		final HttpCardProviderFactory cardProviderFactory = new HttpCardProviderFactory(cardDownloadManager);
		final CardSetProcessor cardSetProcessor = new CardSetProcessor(cardProviderFactory);
		final HttpCardSetProviderFactory cardSetProviderFactory = new HttpCardSetProviderFactory(http, cardSetProcessor);
		this.siteMapProcessor = new SiteMapProcessor(cardSetProviderFactory);
	}
	
	@Override
	public @Nonnull Catalog requestCatalog() throws Exception {
		logger.fine("Downloading catalog");
		return http.requestDocument("/sitemap.html", siteMapProcessor);
	}
	
	@Override
	public List<? extends CardSetReference> requestCardSetReferences(String languageAbbr) throws Exception {
		logger.fine("Request card set references");
		final Catalog catalog = requestCatalog();
		final Language language = catalog.getLanguageByAbbr(languageAbbr);
		final ArrayList<CardSetReference> references = new ArrayList<CardSetReference>();
		for (Block block : language.getBlocks()) {
			references.addAll(block.getSetReferences());
		}
		references.trimToSize();
		return references;
	}
	
	@Override
	public @Nonnull List<? extends CardSet> requestCardSetsByAbbrAllOrNone(@Nonnull String languageAbbr, @Nonnull String[] cardSetAbbrs) throws Exception {
		final Catalog catalog = requestCatalog();
		final ArrayList<CardSet> cardSets = new ArrayList<CardSet>();
		for (String cardSetAbbr : cardSetAbbrs) {
			final CardSetReference cardSetReference = catalog.getSetReferenceByAbbr(languageAbbr, cardSetAbbr);
			cardSets.add(requestCardSet(cardSetReference));
		}
		return cardSets;
	}
	
	@Override
	public CardSet requestCardSet(CardSetReference cardSetReference) throws Exception {
		return ((HttpCardSetReference)cardSetReference).requestCardSet();
	}
	
	public void shutdown() {
		if(cardDownloadManager != null) {
			cardDownloadManager.shutdown();
		}
	}

}
