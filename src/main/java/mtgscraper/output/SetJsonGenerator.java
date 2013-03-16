package mtgscraper.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import mtgscraper.Visitor;
import mtgscraper.entities.Card;
import mtgscraper.entities.CardSet;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

public class SetJsonGenerator {
	private static final Logger logger = Logger.getLogger(SetJsonGenerator.class.getName());
	
	/**
	 * Saves the to the specified file
	 */
	public void saveSet(CardSet set, File outputFile) throws IOException {
		FileOutputStream fileOutStream = null;
		try {
			fileOutStream = new FileOutputStream(outputFile);
			saveSet(set, fileOutStream);
		} finally {
			if(fileOutStream != null) {
				fileOutStream.close();
			}
		}
	}
	
	public void saveSet(CardSet set, OutputStream outputStream) throws IOException {
		logger.fine("Saving set index for " + set.getName());
		JsonFactory jsonFactory = new JsonFactory();
		
		JsonGenerator generator = jsonFactory.createGenerator(outputStream);
		
		generator.writeStartObject();
		generator.writeStringField("language", set.getLanguage());
		generator.writeStringField("abbreviation", set.getAbbr());
		generator.writeStringField("name", set.getName());
		
		generator.writeArrayFieldStart("cards");
		set.eachCard(new CardGeneratorVistor(generator));
		generator.writeEndArray();
		
		generator.writeEndObject();
		
		generator.flush();
		logger.fine("Finished set index " + set.getName());
	}
	
	private static class CardGeneratorVistor implements Visitor<Card> {
		private static final Logger logger = Logger.getLogger(CardGeneratorVistor.class.getName());
		private JsonGenerator generator;
		
		public CardGeneratorVistor(JsonGenerator generator) {
			this.generator = generator;
		}
		
		@Override
		public void visit(Card card) {
			try {
				
				generator.writeStartObject();
				generator.writeStringField("index", card.getIndex());
				generator.writeStringField("name", card.getName());
				generator.writeStringField("typeLine", card.getTypeLine());
				if(card.getBodyText() != null) {
					generator.writeStringField("body", card.getBodyText());
				}
				if(card.getPowerToughness() != null) {
					generator.writeStringField("powerToughness", card.getPowerToughness());
				}
				if(card.getCastingCost() != null) {
					generator.writeStringField("castingCost", card.getCastingCost());
				}
				if(card.getLoyalty() != null) {
					generator.writeStringField("loyalty", card.getLoyalty());
				}
				generator.writeStringField("rarity", card.getRarity());
				generator.writeStringField("artist", card.getArtist());
				generator.writeEndObject();
				generator.flush();
				logger.fine("Finished " + card.getIndex() + " - " + card.getName());
				
			} catch(JsonGenerationException exc) {
				throw new RuntimeException(exc);
			} catch(IOException exc) {
				throw new RuntimeException(exc);
			}
		}
	}
}