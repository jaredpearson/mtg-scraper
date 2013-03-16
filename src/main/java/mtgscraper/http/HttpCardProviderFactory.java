package mtgscraper.http;

import java.util.List;

public class HttpCardProviderFactory {
	private CardDownloadManager cardDownloadManager;
	
	public HttpCardProviderFactory(CardDownloadManager cardDownloadManager) {
		this.cardDownloadManager = cardDownloadManager;
	}
	
	/**
	 * Creates a new HTTP Card provider for the given cards.
	 */
	public HttpCardProvider getForCards(List<CardLink> cards) {
		return new HttpCardProvider(cardDownloadManager, cards);
	}
}
