package apaapi4j;

import java.util.List;

public class ItemAttributes {
	public final List<String> authors;
	public final List<String> directors;
	public final List<String> manufacturers;
	public final List<Creator> creators;
	public final String productGroup;
	public final String title;
	
	public ItemAttributes(List<String> authors, List<String> directors,
			List<String> manufacturers, List<Creator> creators,
			String productGroup, String title) {
		this.authors = authors;
		this.directors = directors;
		this.manufacturers = manufacturers;
		this.creators = creators;
		this.productGroup = productGroup;
		this.title = title;
	}

	@Override
	public String toString() {
		return "ItemAttributes [authors=" + authors + ", directors="
				+ directors + ", manufacturers=" + manufacturers
				+ ", creators=" + creators + ", productGroup="
				+ productGroup + ", title=" + title + "]";
	}
}

