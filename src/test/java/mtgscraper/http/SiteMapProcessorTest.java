package mtgscraper.http;

import static org.mockito.Mockito.*;
import static mtgscraper.http.HttpTestUtils.*;

import mtgscraper.entities.Block;
import mtgscraper.entities.Catalog;
import mtgscraper.entities.Language;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

public class SiteMapProcessorTest {
	@Test
	public void testProcess() throws Exception {
		HttpCardSetProviderFactory cardSetProviderFactory = mock(HttpCardSetProviderFactory.class);
		
		Document document = loadDocumentFromResource("sitemap.html");
		Catalog catalog = (new SiteMapProcessor(cardSetProviderFactory)).process(document);
		
		Assert.assertNotNull("Process should never return null", catalog);
		Assert.assertEquals("Expected the languages to be read from the page", 11, catalog.getLanguages().size());
		
		Language language = catalog.getLanguageByAbbr("en");
		Assert.assertNotNull("Expected the \"en\" language to have been read from the page", language);
		Assert.assertEquals("Expected the blocks to be read from the page", 40, language.getBlocks().size());
		
		Block block = language.getBlocks().get(0);
		Assert.assertNotNull("Expected the first block to not be null", block);
	}
}
