package mtgscraper.http;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Http {
	private static final Logger logger = Logger.getLogger(Http.class.getName());
	private static final String REQUEST_METHOD_GET = "GET";
	private static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";
	private static final String DEFAULT_CONTENT_TYPE = "text/plain; charset=utf-8";
	private @Nullable final String baseUri;
	
	/**
	 * Creates a new Http instance
	 */
	public Http() {
		this(null);
	}
	
	/**
	 * Creates a new Http instance
	 * @param baseUri Base URI to use if a relative URL is requested.
	 */
	public Http(@Nullable String baseUri) {
		this.baseUri = baseUri;
	}
	
	/**
	 * Requests a document using HTTP for the given URL and processes the document with processor.
	 */
	public  @Nonnull <T> T requestDocument(@Nonnull final String url, @Nonnull final Processor<Document, T> processor) 
			throws IOException {
		Document document = requestDocument(url);
		return processor.process(document);
	}
	
	/**
	 * Requests the document for the given URL returning back a JSoup Document
	 */
	public @Nonnull Document requestDocument(@Nonnull final String url) throws IOException {
		if(url.isEmpty()) {
			throw new IllegalArgumentException("URL is blank");
		}
		
		String requestUrl = url;
		if(!url.toLowerCase().startsWith("http")) {
			if(baseUri != null) {
				requestUrl = baseUri + url;
			} else {
				throw new IllegalArgumentException("URL specified is not full. It must begin with HTTP.");
			}
		}
		
		Document document = null;
		HttpURLConnection conn = null;
		try {
			logger.fine("Requesting document at url: " + requestUrl);
			conn = (HttpURLConnection)(new URL(requestUrl)).openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
			conn.connect();
			
			String contentEncoding = conn.getContentEncoding();
			document = Jsoup.parse(conn.getInputStream(), contentEncoding, requestUrl);
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return document;
	}
	
	/**
	 * Downloads an image from the given URL and saves it to the file.
	 */
	public void downloadImage(@Nonnull final String url, @Nonnull final String formatName, @Nonnull final File file) throws IOException {
		BufferedImage image = ImageIO.read(new URL(url));
		ImageIO.write(image, formatName, file);
	}

	public interface Processor<T, S> {
		@Nonnull
		public S process(@Nonnull T value) throws IOException;
	}
}