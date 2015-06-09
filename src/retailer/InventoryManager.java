package retailer;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import tools.Item;
import tools.XmlFileController;

/**
 * @author comp6231.team5
 *
 */
public class InventoryManager {
	public HashMap<String, Item> inventoryItemMap;
	private String warehouseName;
	public InventoryManager(String warehouseName){
		inventoryItemMap = new HashMap<String, Item>();
		this.warehouseName = warehouseName;
		loadItems();
	}
	
	/**
	 * load the inventory list of the current warehouseName from the corresponding xml file
	 * and save the list to inventoryItemMap
	 */
	private void loadItems(){
		XmlFileController xmlfile = new XmlFileController(warehouseName + ".xml");
		Element root = xmlfile.Read();
		if(root != null){
			List<Element> nodes = root.elements("item");
			for(Element subElem: nodes){
				String manufacturerName = subElem.element("manufacturerName").getText();
				String productType = subElem.element("productType").getText();
				float unitPrice = Float.parseFloat(subElem.element("unitPrice").getText());
				int quantity = Integer.parseInt(subElem.element("quantity").getText());
				
				inventoryItemMap.put(manufacturerName + productType, new Item(manufacturerName, productType, unitPrice, quantity));
			}
		}
	}
	

	/**
	 * save inventoryItemMap to the corresponding xml file
	 */
	public void saveItems()
	{
		XmlFileController xmlFileControler = new XmlFileController(warehouseName + ".xml");
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("items");
		for(Item item: inventoryItemMap.values()){
			Element itemElem = item.toXmlElement();
			root.add(itemElem);
		}
		try {
			xmlFileControler.Write(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
