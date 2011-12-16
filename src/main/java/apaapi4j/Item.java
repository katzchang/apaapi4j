package apaapi4j;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Item {
	public static Item parseItem(Node itemNode) {
		String asin = null;
		String detailPageURL = null;
		List<ItemLink> itemLinks = new ArrayList<ItemLink>();
		ItemAttributes itemAttributes = null;
		
		NodeList children = itemNode.getChildNodes();
		for (int j = 0; j < children.getLength(); j++) {
			Node child = children.item(j);
			if (child.getNodeType() == org.w3c.dom.Node.TEXT_NODE) continue;
			String nodeName = child.getNodeName();
			if (nodeName.equals("ASIN")) asin = child.getTextContent();
			if (nodeName.equals("DetailPageURL")) detailPageURL = child.getTextContent();
			if (nodeName.equals("ItemLinks")) ;
			if (nodeName.equals("ItemAttributes")) itemAttributes = parseItemAttribbutes(child);
		}
		return new Item(asin, detailPageURL, itemLinks, itemAttributes);
	}

	static ItemAttributes parseItemAttribbutes(Node itemAttributesNode) {
		List<String> authors = new ArrayList<String>();
		List<String> directors = new ArrayList<String>();
		List<String> manufacturers = new ArrayList<String>();
		List<Creator> creators = new ArrayList<Creator>();
		String productGroup = null;
		String title = null;
		NodeList children = itemAttributesNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == org.w3c.dom.Node.TEXT_NODE) continue;
			String nn = child.getNodeName();
			if (nn.equals("Author")) authors.add(child.getTextContent());
			if (nn.equals("Director")) directors.add(child.getTextContent());
			if (nn.equals("Manufacturer")) manufacturers.add(child.getTextContent());
			if (nn.equals("Creator")) {
				creators.add(new Creator(
						child.getAttributes().getNamedItem("Role").getTextContent(),
						child.getTextContent()));
			}
			if (nn.equals("ProductGroup")) productGroup = child.getTextContent();
			if (nn.equals("Title")) title = child.getTextContent();
		}
		return new ItemAttributes(authors, directors, manufacturers, creators, productGroup, title);
	}
	
	public final String asin;
	public final String detailPageURL;
	public final List<ItemLink> itemLinks;
	public final ItemAttributes itemAttributes;
	
	public Item(String asin, String detailPageURL, List<ItemLink> itemLinks,
			ItemAttributes itemAttributes) {
		this.asin = asin;
		this.detailPageURL = detailPageURL;
		this.itemLinks = itemLinks;
		this.itemAttributes = itemAttributes;
	}

	@Override
	public String toString() {
		return "Item [asin=" + asin + ", detailPageURL=" + detailPageURL
				+ ", itemLinks=" + itemLinks + ", itemAttributes="
				+ itemAttributes + "]";
	}
	
}
