package mtgscraper;

import javax.annotation.Nonnull;

public interface Visitor<T> {
	public void visit(@Nonnull T value);
}