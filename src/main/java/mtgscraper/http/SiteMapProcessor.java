package mtgscraper.http;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nonnull;

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
	private @Nonnull final HttpCardSetProviderFactory cardSetProviderFactory;
	
	public SiteMapProcessor(@Nonnull final HttpCardSetProviderFactory cardSetProviderFactory) {
		this.cardSetProviderFactory = cardSetProviderFactory;
	}
	
	@Override
	public @Nonnull Catalog process(@Nonnull final Document document) throws IOException {
		ArrayList<Language> languages = new ArrayList<Language>();
		Elements languageElements = document.getElementsByTag("h2");
		for(Element languageElement : languageElements) {

			final String languageName = languageElement.ownText();
			final String languageAbbr = languageElement.getElementsByTag("small").first().ownText().toLowerCase();
			
			ArrayList<Category> categories = new ArrayList<Category>();
			Elements categoryElements = languageElement.nextElementSibling().getElementsByTag("h3");
			for(Element categoryElement : categoryElements) {
				
				ArrayList<Block> blocks = new ArrayList<Block>();
				Elements blockElements = categoryElement.nextElementSibling().children();
				for(Element blockElement : blockElements) {

					ArrayList<HttpCardSetReference> sets = new ArrayList<HttpCardSetReference>();
					Elements setElements = blockElement.getElementsByTag("li");
					for(Element setElement : setElements) {
						//skip the self element
						if(setElement == blockElement) {
							continue;
						}
						
						String setName = setElement.getElementsByTag("a").get(0).ownText();
						String setUrl = setElement.getElementsByTag("a").attr("href");
						String setAbbr = setElement.getElementsByTag("small").first().ownText().toUpperCase();
						sets.add(new HttpCardSetReference(setUrl, setName, setAbbr, languageAbbr, cardSetProviderFactory.create()));
					}
					sets.trimToSize();
					
					String blockName = blockElement.ownText();
					blocks.add(new Block(blockName, sets));
				}
				blocks.trimToSize();
				
				//create the category
				String categoryName = categoryElement.ownText();
				categories.add(new Category(categoryName, blocks));
			}
			categories.trimToSize();
			
			languages.add(new Language(languageName, languageAbbr, categories));
		}
		languages.trimToSize();
		return new Catalog(languages);
	}
}