package apaapi4j;

import java.util.Collections;
import java.util.List;

public class ItemSearchResponse {
	public final List<Item> items;
	public final Integer totalResult;
	public final Integer totalPages;

	public ItemSearchResponse(List<Item> items, Integer totalResult,
			Integer totalPages) {
		this.items = Collections.synchronizedList(items);
		this.totalResult = totalResult;
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "ItemSearchResponse [items=" + items + ", totalResult="
				+ totalResult + ", totalPages=" + totalPages + "]";
	}

}
