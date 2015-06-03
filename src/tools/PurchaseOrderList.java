/**
 * 
 */
package tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

/**
 * @author Ting Zhang 
 * @date 2015-05-25
 */
public class PurchaseOrderList {
	
	Map<String , PurchaseOrder> purchaseOrderList = new HashMap<>();
	
	public Map<String , PurchaseOrder> getPurchaseOrderList(){
		return purchaseOrderList;
	}
	
	/**
	 * Save purchaseOrder for a specific manufacturer to the XML file
	 * 
	 * @param purchase order and manufacturer name
	 *            
	 * @throws Exception
	 */
public void replenish(String manufacturerName)throws Exception{
		
		String filepath = new String(System.getProperty("user.dir") + "/src/com/manufacturer/Data/" + manufacturerName + "_orders.xml");
		FileManager xmlfile = new FileManager(filepath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("orders");
		Object[] orders=purchaseOrderList.values().toArray();
		for (int i = 0; i < orders.length; i++) {
			PurchaseOrder it = (PurchaseOrder)orders[i];
			Element ml = it.saveToElement();
			root.add(ml);
		}

		xmlfile.Write(document);
	}
	
	/**
	 * Load all the purchase orders for a specific manufacturer from the XML file
	 * 
	 * @param manufacturer name
	 *           
	 * @throws Exception
	 * 
	 * @return a map which stores all the  purchase orders with the key as the orderNum
	 */
	public void loadPurchaseOrders(String mfn)throws Exception{
		Map<String , PurchaseOrder> orderList = new HashMap<>();
		String filepath = new String(System.getProperty("user.dir")
				+ "/src/com/manufacturer/Data/" + mfn + "_orders.xml");
		FileManager xmlfile = new FileManager(filepath);
		Element root = xmlfile.Read();
		List<Element> nodes = root.elements("order");
		for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
			Element me = (Element) it.next();
			PurchaseOrder nm = new PurchaseOrder();
			nm = PurchaseOrder.load(me);
			nm.setManufacturername(mfn);
			purchaseOrderList.put(nm.getOrderNum(), nm);
		}
	
	}

}
