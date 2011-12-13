package apaapi4j;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apaapi4j.helper.Helper;

public class ParserTest {
	static Logger logger = LoggerFactory.getLogger("test");
	static {
		Helper.setLogger(logger);
	}
	
	@Test
	public void testdocParser() throws Exception {
		ItemSearchResponse itemSearchResponse = AmazonAPIService.parseBookSearch("src/test/resources/ItemSearchResponse_2.xml");
		
		assertThat(itemSearchResponse.totalResult, is(36));
		assertThat(itemSearchResponse.totalPages, is(4));
		assertThat(itemSearchResponse.items.size(), is(10));
		
		Item item = itemSearchResponse.items.get(9);
		assertThat(item.asin, is("4884183223"));
		assertThat(item.detailPageURL, is("http://www.amazon.co.jp/SWITCH-Vol-29-No-7%EF%BC%88-2011%E5%B9%B47%E6%9C%88%E5%8F%B7-%EF%BC%89%E7%89%B9%E9%9B%86%EF%BC%9A%E3%82%BD%E3%83%BC%E3%82%B7%E3%83%A3%E3%83%AB%E3%82%AB%E3%83%AB%E3%83%81%E3%83%A3%E3%83%BC%E3%83%8D%E7%94%B31oo/dp/4884183223%3FSubscriptionId%3DAKIAJDL6OQHWMK7E2VXQ%26tag%3Dtrukxkxn-22%26linkCode%3Dxm2%26camp%3D2025%26creative%3D165953%26creativeASIN%3D4884183223"));
		assertThat(item.itemLinks.isEmpty(), is(true));
		assertThat(item.itemAttributes.title, is("SWITCH Vol.29 No.7（ 2011年7月号 ）特集：ソーシャルカルチャーネ申1oo"));
		assertThat(item.itemAttributes.authors.get(0), is("新井敏記"));
		assertThat(item.itemAttributes.creators.get(0).role, is("編集"));
		assertThat(item.itemAttributes.creators.get(0).creator, is("菅原豪"));
		assertThat(item.itemAttributes.manufacturers.get(0), is("スイッチパブリッシング"));
		assertThat(item.itemAttributes.productGroup, is("Book"));
	}
	
}
