package mtgscraper.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
	public void saveSet(@Nonnull final CardSet set, @Nonnull final File outputFile) throws IOException {
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
	
	public void saveSet(@Nonnull final CardSet set, @Nonnull final OutputStream outputStream) throws IOException {
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
		private @Nonnull final JsonGenerator generator;
		
		public CardGeneratorVistor(@Nonnull final JsonGenerator generator) {
			this.generator = generator;
		}
		
		@Override
		public void visit(@Nonnull final Card card) {
			try {
				outputCard(generator, card);
			} catch(JsonGenerationException exc) {
				throw new RuntimeException(exc);
			} catch(IOException exc) {
				throw new RuntimeException(exc);
			}
		}
	}
	
	private static void outputCard(@Nonnull final JsonGenerator generator, @Nonnull final Card card)
			throws JsonGenerationException, IOException {
		generator.writeStartObject();
		generator.writeStringField("index", card.getIndex());
		generator.writeStringField("name", card.getName());
		generator.writeStringField("typeLine", card.getTypeLine());
		writeStringFieldIfNotNull(generator, "body", card.getBodyText());
		writeStringFieldIfNotNull(generator, "powerToughness", card.getPowerToughness());
		writeStringFieldIfNotNull(generator, "castingCost", card.getCastingCost());
		writeStringFieldIfNotNull(generator, "loyalty", card.getLoyalty());
		generator.writeStringField("rarity", card.getRarity());
		generator.writeStringField("artist", card.getArtist());
		generator.writeEndObject();
		generator.flush();
		logger.fine("Finished " + card.getIndex() + " - " + card.getName());
	}
	
	private static void writeStringFieldIfNotNull(@Nonnull final JsonGenerator generator, @Nonnull final String fieldName, 
			@Nullable final String value) throws JsonGenerationException, IOException {
		if(value != null) {
			generator.writeStringField(fieldName, value);
		}
	}
}