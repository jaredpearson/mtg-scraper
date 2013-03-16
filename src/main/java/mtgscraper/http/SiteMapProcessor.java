package mtgscraper.http;

import java.io.IOException;
import java.util.ArrayList;

import mtgscraper.entities.Block;
import mtgscraper.entities.Catalog;
import mtgscraper.entities.Category;
import mtgscraper.entities.Language;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Takes the magiccards.info set directory (aka sitemap.html) as an HTML document 
 * and creates a new Catalog from it.
 * @author jared.pearson
 */
public class SiteMapProcessor implements Http.Processor<Document, Catalog> {
	private HttpCardSetProviderFactory cardSetProviderFactory;
	
	public SiteMapProcessor(HttpCardSetProviderFactory cardSetProviderFactory) {
		this.cardSetProviderFactory = cardSetProviderFactory;
	}
	
	@Override
	public Catalog process(Document document) throws IOException {
		ArrayList<Language> languages = new ArrayList<Language>();
		Elements languageElements = document.getElementsByTag("h2");
		for(Element languageElement : languageElements) {
			
			ArrayList<Category> categories = new ArrayList<Category>();
			Elements categoryElements = languageElement.nextElementSibling().getElementsByTag("h3");
			for(Element categoryElement : categoryElements) {
				
				ArrayList<Block> blocks = new ArrayList<Block>();
				Elements blockElements = categoryElement.nextElementSibling().children();
				for(Element blockElement : blockElements) {

					ArrayList<CardSetLink> sets = new ArrayList<CardSetLink>();
					Elements setElements = blockElement.getElementsByTag("li");
					for(Element setElement : setElements) {
						//skip the self element
						if(setElement == blockElement) {
							continue;
						}
						
						String setName = setElement.getElementsByTag("a").get(0).ownText();
						String setUrl = setElement.getElementsByTag("a").attr("href");
						String setAbbr = setElement.getElementsByTag("small").first().ownText().toUpperCase();
						sets.add(new CardSetLink(setName, setAbbr, setUrl));
					}
					sets.trimToSize();
					
					String blockName = blockElement.ownText();
					blocks.add(new Block(blockName, cardSetProviderFactory.getForSets(sets)));
				}
				blocks.trimToSize();
				
				//create the category
				String categoryName = categoryElement.ownText();
				categories.add(new Category(categoryName, blocks));
			}
			categories.trimToSize();

			String languageName = languageElement.ownText();
			String languageAbbr = languageElement.getElementsByTag("small").first().ownText().toLowerCase();
			
			languages.add(new Language(languageName, languageAbbr, categories));
		}
		languages.trimToSize();
		return new Catalog(languages);
	}
}