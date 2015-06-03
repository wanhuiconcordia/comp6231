/**
 * 
 */
package tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

/**
 * @author Ting Zhang
 * @date 2015-05-25
 */
public class InventoryList {
	ArrayList<Inventory> inventoryList = new ArrayList<>();
	
	public ArrayList<Inventory> getInventoryList(){
		return inventoryList;
	}
	
	/**
	 * Save inventoryList to the XML file
	 * 
	 * @param warehouse name
	 *            
	 * @throws Exception
	 */
	public void replenish(String warehouseName)throws Exception{
		
		String filepath = new String(System.getProperty("user.dir") + "/src/xml/" + warehouseName + "_inventoryRecords.xml");
		FileManager xmlfile = new FileManager(filepath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document document = DocumentHelper.createDocument();
		
		Element root = document.addElement("records");
		Object[] records=inventoryList.toArray();
		for (int i = 0; i < records.length; i++) {
			Inventory it = (Inventory)records[i];
			Element ml = it.saveToElement();
			root.add(ml);
		}

		xmlfile.Write(document);
		
	}
	
	/**
	 * Load all the inventory records from the XML file
	 * 
	 * @param warehouse name
	 *           
	 * @throws Exception
	 */
	public void loadInventoryList(String warehouseName)throws Exception{
		
		String filepath = new String(System.getProperty("user.dir")
				+ "/src/xml/" + warehouseName + "_inventoryRecords.xml");
		FileManager xmlfile = new FileManager(filepath);
		Element root = xmlfile.Read();
		List<Element> nodes = root.elements("record");
		for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
			Element me = (Element) it.next();
			Inventory nm = new Inventory();
			nm = Inventory.loadItem(me);
			this.inventoryList.add(nm);
		}
		
	}
	
	

}
