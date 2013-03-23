package mtgscraper.http;

import java.util.List;

import javax.annotation.Nonnull;

public class HttpCardProviderFactory {
	private final CardDownloadManager cardDownloadManager;
	
	public HttpCardProviderFactory(@Nonnull final CardDownloadManager cardDownloadManager) {
		this.cardDownloadManager = cardDownloadManager;
	}
	
	/**
	 * Creates a new HTTP Card provider for the given cards.
	 */
	public @Nonnull HttpCardProvider getForCards(@Nonnull final List<CardLink> cards) {
		return new HttpCardProvider(cardDownloadManager, cards);
	}
}
