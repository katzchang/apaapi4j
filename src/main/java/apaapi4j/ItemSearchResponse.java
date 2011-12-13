package apaapi4j;

import java.util.Collections;
import java.util.List;

public class ItemSearchResponse {
	final List<Item> items;
	final Integer totalResult;
	final Integer totalPages;
	
	public ItemSearchResponse(List<Item> items, Integer totalResult,
			Integer totalPages) {
		this.items = Collections.synchronizedList(items);
		this.totalResult = totalResult;
		this.totalPages = totalPages;
	}
	
	
}
