package apaapi4j;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apaapi4j.AmazonAPIService;
import apaapi4j.ItemSearchResponse;
import apaapi4j.helper.Helper;

public class AmazonAPIServiceTest {

	AmazonAPIService service;
	static Logger logger = LoggerFactory.getLogger("test");
	static {
		Helper.setLogger(logger);
	}
	
	@Before
	public void before() {
		Config config = new Config();
		this.service = new AmazonAPIService(config);
	}
	
	@Test
	public void searchBooksByKeywords() {
		ItemSearchResponse response1 = service.searchBooksByKeywords("まどか☆マギカ");
		assertThat(response1.totalResult >= 0, is(true));
		assertThat(response1.totalPages >= 0, is(true));
		assertThat(response1.items.isEmpty(), is(false));
		
		ItemSearchResponse response2 = service.searchBooksByKeywords("まどか☆マギカ", 2);
		assertThat(response2.totalResult, is(response1.totalResult));
		assertThat(response2.totalPages, is(response1.totalPages));
		assertThat(response1.items.isEmpty(), is(false));
		
		ItemSearchResponse responseN = service.searchBooksByKeywords("まどか☆マギカ", response1.totalPages + 1);
		assertThat(responseN.totalResult, is(response1.totalResult));
		assertThat(responseN.totalPages, is(response1.totalPages));
		assertThat(responseN.items.isEmpty(), is(true));
	}
	
	@Test
	public void lookupByValidISBN() {
		Item item = service.findByISBN("9784873114392");
		assertThat(item.itemAttributes.title, is("Hadoop"));
		assertThat(item.asin, is("487311439X"));
	}
	
	@Test
	public void lookupByInalidISBN() {
		assertThat(service.findByISBN("978487311439223456789123459"), is(nullValue()));
	}
	
	@Test
	public void lookupByValidASIN() {
		Item item = service.findByASIN("487311439X");
		assertThat(item.asin, is("487311439X"));
		assertThat(item.itemAttributes.title, is("Hadoop"));
	}
	
	@Test
	public void lookupByInvalidASIN() {
		Item item = service.findByASIN("hogehogehoge");
		assertThat(item, is(nullValue()));
	}
	
}
