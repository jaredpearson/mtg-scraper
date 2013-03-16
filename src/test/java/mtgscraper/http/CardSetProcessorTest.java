package mtgscraper.http;

import static org.mockito.Mockito.*;

import java.util.List;

import mtgscraper.entities.CardSet;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class CardSetProcessorTest {
	
	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testProcess() throws Exception {
		HttpCardProviderFactory cardProviderFactory = mock(HttpCardProviderFactory.class);
		
		Document document = HttpTestUtils.loadDocumentFromResource("cardset.html");
		CardSet cardSet = (new CardSetProcessor(cardProviderFactory)).process(document);
		
		Assert.assertNotNull("Process should never return null", cardSet);
		Assert.assertEquals("Expected the name to be taken from the page", "Return to Ravnica", cardSet.getName()); 
		Assert.assertEquals("Expected the abbreviation to be taken from the page", "RTR", cardSet.getAbbr());
		Assert.assertEquals("Expected the language to be taken from the page", "en", cardSet.getLanguage());
		
		ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
		verify(cardProviderFactory).getForCards(argument.capture());
		
		List cardLinks = argument.getValue();
		Assert.assertNotNull("Expected the factory parameter to not be null", cardLinks);
		Assert.assertEquals("Expected the number of card links to read from the page", 4, cardLinks.size());

		CardLink cardLink1 = (CardLink)cardLinks.get(0);
		Assert.assertEquals("Expected the URL to be set from the page", "http://magiccards.info/rtr/en/1.html", cardLink1.getUrl());
		Assert.assertEquals("Creature — Angel", cardLink1.getTypeLine());
		Assert.assertEquals("5/6", cardLink1.getPowerToughness());
		Assert.assertNull(cardLink1.getLoyalty());
		Assert.assertEquals("4WWW", cardLink1.getCastingCost());
		Assert.assertEquals("Mythic Rare", cardLink1.getRarity());
		Assert.assertEquals("Aleksi Briclot", cardLink1.getArtist());

		CardLink cardLink2 = (CardLink)cardLinks.get(1);
		Assert.assertEquals("Expected the URL to be set from the page", "http://magiccards.info/som/en/169.html", cardLink2.getUrl());
		Assert.assertEquals("Artifact Creature — Construct", cardLink2.getTypeLine());
		Assert.assertEquals("3/5", cardLink2.getPowerToughness());
		Assert.assertNull(cardLink2.getLoyalty());
		Assert.assertEquals("5", cardLink2.getCastingCost());
		Assert.assertEquals("Rare", cardLink2.getRarity());
		Assert.assertEquals("jD", cardLink2.getArtist());

		CardLink cardLink3 = (CardLink)cardLinks.get(2);
		Assert.assertEquals("Expected the URL to be set from the page", "http://magiccards.info/rtr/en/3.html", cardLink3.getUrl());
		Assert.assertEquals("Enchantment — Aura", cardLink3.getTypeLine());
		Assert.assertNull("Expected no power/toughness since card is an Enchantment", cardLink3.getPowerToughness());
		Assert.assertNull(cardLink3.getLoyalty());
		Assert.assertEquals("2W", cardLink3.getCastingCost());
		Assert.assertEquals("Uncommon", cardLink3.getRarity());
		Assert.assertEquals("Greg Staples", cardLink3.getArtist());
		
		CardLink cardLink4 = (CardLink)cardLinks.get(3);
		Assert.assertEquals("Expected the URL to be set from the page", "http://magiccards.info/som/en/6.html", cardLink4.getUrl());
		Assert.assertEquals("Planeswalker — Elspeth", cardLink4.getTypeLine());
		Assert.assertNull("Expected no power/toughness since card is a Planeswalker", cardLink4.getPowerToughness());
		Assert.assertEquals("Expected loyalty to be parsed from page", "4", cardLink4.getLoyalty());
		Assert.assertEquals("3WW", cardLink4.getCastingCost());
		Assert.assertEquals("Mythic Rare", cardLink4.getRarity());
		Assert.assertEquals("Michael Komarck", cardLink4.getArtist());
	}
}
