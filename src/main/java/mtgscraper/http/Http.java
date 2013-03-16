package mtgscraper.http;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Http {
	private static final Logger logger = Logger.getLogger(Http.class.getName());
	private String baseUri;
	
	/**
	 * Creates a new Http instance
	 */
	public Http() {
	}
	
	/**
	 * Creates a new Http instance
	 * @param baseUri Base URI to use if a relative URL is requested.
	 */
	public Http(String baseUri) {
		this.baseUri = baseUri;
	}
	
	/**
	 * Requests a document using HTTP for the given URL and processes the document with processor.
	 */
	public <T> T requestDocument(String url, Processor<Document, T> processor) throws IOException {
		Document document = requestDocument(url);
		return processor.process(document);
	}
	
	/**
	 * Requests the document for the given URL returning back a JSoup Document
	 */
	public Document requestDocument(String url) throws IOException {
		if(url == null || url.isEmpty()) {
			throw new IllegalArgumentException("URL is blank");
		}
		
		if(!url.toLowerCase().startsWith("http")) {
			if(baseUri != null) {
				url = baseUri + url;
			} else {
				throw new IllegalArgumentException("URL specified is not full. It must begin with HTTP.");
			}
		}
		
		Document document = null;
		HttpURLConnection conn = null;
		try {
			logger.fine("Requesting document at url: " + url);
			conn = (HttpURLConnection)(new URL(url)).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			conn.connect();
			
			String contentEncoding = conn.getContentEncoding();
			document = Jsoup.parse(conn.getInputStream(), contentEncoding, url);
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
	public void downloadImage(String url, String formatName, File file) throws IOException {
		BufferedImage image = ImageIO.read(new URL(url));
		ImageIO.write(image, formatName, file);
	}

	public interface Processor<T, S> {
		public S process(T document) throws IOException;
	}
}