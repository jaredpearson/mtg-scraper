package mtgscraper;

public interface Visitor<T> {
	public void visit(T value);
}