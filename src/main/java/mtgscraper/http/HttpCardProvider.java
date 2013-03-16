package mtgscraper.http;

import java.util.List;

import mtgscraper.Visitor;
import mtgscraper.entities.Card;
import mtgscraper.entities.CardSet;

public class HttpCardProvider implements CardSet.CardProvider {
	private List<CardLink> cardLinks;
	private CardDownloadManager cardDownloadManager;
	private List<Card> cards;
	
	public HttpCardProvider(CardDownloadManager cardDownloadManager, List<CardLink> cardLinks) {
		this.cardDownloadManager = cardDownloadManager;
		this.cardLinks = cardLinks;
	}
	
	@Override
	public int getTotal() {
		return cardLinks.size();
	}
	
	@Override
	public void eachCard(final Visitor<Card> visitor) {
		if(cards == null) {
			requestCards(visitor);
		} else {
			for(Card card : cards) {
				visitor.visit(card);
			}
		}
	}
	
	/**
	 * Downloads the cards using the download manager.
	 * @param visitor
	 */
	private void requestCards(final Visitor<Card> visitor) {
		
		Visitor<List<Card>> finished = new Visitor<List<Card>>() {
			@Override
			public void visit(List<Card> value) {
				//when the complete list is finished downloading,
				//save a copy within the provider
				cards = value;
			}
		};
		
		cardDownloadManager.requestCards(cardLinks, visitor, finished);
		
		//wait on the current thread until all of the cards have finished downloading
		while(cards == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}