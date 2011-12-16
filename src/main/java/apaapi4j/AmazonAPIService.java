/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file 
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License. 
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */

package apaapi4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static apaapi4j.helper.Helper.*;

public class AmazonAPIService {
	public static final String VERSION = "2011-08-01";
	
	final String accessKey;
	final String secretKey;
	final String endpoint;
	final String associateTag;
	final SignedRequestsHelper helper;
	
	public AmazonAPIService(String accessKey, String secretKey,
			String endpoint, String associateTag) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.endpoint = endpoint;
		this.associateTag = associateTag;
		helper = SignedRequestsHelper.getInstance(endpoint,
				accessKey, secretKey);
	}

	public AmazonAPIService(Config config) {
		this(config.aws_access_key_id, config.aws_secret_key,
				config.endpoint, config.associate_tag);
	}

	public ItemSearchResponse searchBooksByKeywords(String keywords) {
		return searchBooksByKeywords(keywords, 1);
	}

	public ItemSearchResponse searchForeignBooksByKeywords(String keywords) {
		return searchForeignBooksByKeywords(keywords, 1);
	}
	
	public ItemSearchResponse searchBooksByKeywords(String keywords, Integer page) {
		try {
			String requestUrl = helper.sign(buildSearchParams(keywords, "Books", page));
			return parseBookSearch(requestUrl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ItemSearchResponse searchForeignBooksByKeywords(String keywords, Integer page) {
		try {
			String requestUrl = helper.sign(buildSearchParams(keywords, "ForeignBooks", page));
			return parseBookSearch(requestUrl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	Map<String, String> buildSearchParams(String keywords, String searchIndex, Integer page) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", VERSION);
		params.put("Operation", "ItemSearch");
		params.put("AssociateTag", associateTag);
		params.put("Keywords", keywords);
		params.put("SearchIndex", searchIndex);
		params.put("ItemPage", page.toString());
		return params;
	}

	static ItemSearchResponse parseBookSearch(String url) {
		Document doc = parseXML(url);
		if (logger.isDebugEnabled()) logger.debug(xmlToString(doc)); 
		Integer totalResult = Integer.valueOf(doc.getElementsByTagName("TotalResults").item(0).getTextContent());
		Integer totalPages = Integer.valueOf(doc.getElementsByTagName("TotalPages").item(0).getTextContent());
		List<Item> items = new ArrayList<Item>();
		int itemCount = doc.getElementsByTagName("Item").getLength();
		for (int i = 0; i < itemCount; i++) {
			Node item = doc.getElementsByTagName("Item").item(i);
			items.add(Item.parseItem(item));
		}
		
		return new ItemSearchResponse(items, totalResult, totalPages);
	}

	static Item parseLookUp(String url) {
		Document doc = parseXML(url);
		if (logger.isDebugEnabled()) logger.debug(xmlToString(doc)); 
		if (doc.getElementsByTagName("Error").getLength() >= 1) return null;
		if (doc.getElementsByTagName("Item").getLength() == 0) return null;
		Node itemNode = doc.getElementsByTagName("Item").item(0);
		return Item.parseItem(itemNode);
	}

	public Item findByISBN(String isbn) {
		Item item = findBookByISBN(isbn);
		return item == null ? findForeignBookByISBN(isbn) : item;
	}

	public Item findBookByISBN(String isbn) {
		ItemSearchResponse response = searchBooksByKeywords(isbn);
		return response.items.isEmpty() ? null: response.items.get(0);
	}

	public Item findForeignBookByISBN(String isbn) {
		ItemSearchResponse response = searchForeignBooksByKeywords(isbn);
		return response.items.isEmpty() ? null: response.items.get(0);
	}

	public Item findByASIN(String asin) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", VERSION);
		params.put("Operation", "ItemLookup");
		params.put("AssociateTag", associateTag);
		params.put("ItemId", asin);
		String requestUrl = helper.sign(params);
		return parseLookUp(requestUrl);
	}

}
