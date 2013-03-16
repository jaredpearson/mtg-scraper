package mtgscraper.http;

public class CardSetLink {
	private String name;
	private String abbr;
	private String url;
	
	public CardSetLink(String name, String abbr, String url) {
		this.name = name;
		this.abbr = abbr;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAbbr() {
		return abbr;
	}
	
	public String getUrl() {
		return url;
	}
}