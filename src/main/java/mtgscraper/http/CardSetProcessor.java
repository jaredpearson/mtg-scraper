package mtgscraper.http;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mtgscraper.entities.CardSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * After a card set document is retrieved, this processor converts the value retrieved
 * into the {@link CardSet} object. 
 * @author jared.pearson
 */
public class CardSetProcessor implements Http.Processor<Document, CardSet> {
	private @Nonnull final HttpCardProviderFactory cardProviderFactory;
	
	public CardSetProcessor(@Nonnull final HttpCardProviderFactory cardProviderFactory) {
		assert cardProviderFactory != null;
		this.cardProviderFactory = cardProviderFactory;
	}
	
	@Override
	public @Nonnull CardSet process(@Nonnull final Document document) throws IOException {
		assert document != null;
		Element header = document.getElementsByTag("h1").first();
		String setName = header.ownText(); 
		
		String setLanguageText = header.getElementsByTag("small").first().ownText();
		String setAbbr = setLanguageText.substring(0, setLanguageText.indexOf('/')).toUpperCase();
		String setLanguage = setLanguageText.substring(setLanguageText.indexOf('/') + 1).toLowerCase();
		
		Element cardListTable = document.getElementsByTag("table").get(3);
		Elements cardListRows = cardListTable.getElementsByTag("tr");
		
		//skip the first row because it is the table header
		
		ArrayList<CardLink> cards = new ArrayList<CardLink>();
		for(int index = 1; index < cardListRows.size(); index++) {
			Element cardListRow = cardListRows.get(index);
			Elements cardListCells = cardListRow.getElementsByTag("td");
			
			Element cardLink = cardListCells.get(1).getElementsByTag("a").first();
			String url = cardLink.absUrl("href");
			
			String typeLine = cardListCells.get(2).text();
			
			//parse the power/toughness, which should be in format of
			//Creature — Zombie 2/2
			String powerToughness = null;
			if(typeLine.startsWith("Creature") || typeLine.startsWith("Artifact Creature") || typeLine.startsWith("Legendary Creature")) {
				powerToughness = typeLine.substring(typeLine.lastIndexOf(" ") + 1);
				typeLine = typeLine.substring(0, typeLine.lastIndexOf(" "));
			}
			
			//parse the loyalty which should be in the format of
			//Planeswalker — Liliana (Loyalty: 3)
			String loyalty = null;
			if(typeLine.startsWith("Planeswalker")) {
				loyalty = typeLine.substring(typeLine.lastIndexOf(": ") + 2, typeLine.length() - 1);
				typeLine = typeLine.substring(0, typeLine.lastIndexOf(" ("));
			}
			typeLine = trimToNull(typeLine);

			String castingCost = trimToNull(cardListCells.get(3).text());
			String rarity = trimToNull(cardListCells.get(4).text());
			String artist = trimToNull(cardListCells.get(5).text());
			
			cards.add(new CardLink(url, typeLine, powerToughness, loyalty, castingCost, rarity, artist));
		}
		cards.trimToSize();
		
		HttpCardProvider cardProvider = cardProviderFactory.getForCards(cards);
		CardSet cardSet = new CardSet(setName, setAbbr, setLanguage, cardProvider);
		return cardSet;
	}
	
	private static @Nullable String trimToNull(@Nullable String value) {
		if(value == null) {
			return null;
		}
		
		String trimmed = value.trim();
		if(trimmed.length() == 0) {
			return null;
		}
		return trimmed;
	}
}