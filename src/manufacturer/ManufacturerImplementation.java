package manufacturer;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import tools.Item;
import tools.LoggerClient;
import tools.Product;
import tools.XmlFileController;

/**
 * @author comp6231.team5
 * Implement the ManufacturerInterface and extends UnicastRemoteObject
 */
public class ManufacturerImplementation extends UnicastRemoteObject implements ManufacturerInterface{
	private static final long serialVersionUID = 1L;
	private String name;
	private HashMap<String, Item> purchaseOrderMap;
	private int orderNum;
	private LoggerClient loggerClient;
	private PurchaseOrderManager purchaseOrderManager;
	/**
	 * Constructor
	 * @param name
	 * @param loggerClient
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public ManufacturerImplementation(String name, LoggerClient loggerClient)throws RemoteException, MalformedURLException, NotBoundException{
		this.name = name;
		this.loggerClient = loggerClient;
		purchaseOrderMap = new HashMap<String, Item>();
		orderNum = 1000;
		purchaseOrderManager = new PurchaseOrderManager(name);
		setProduct();
		System.out.println("ManufacturerImplementation constructed:" + name);
	}
	
	/**
	 * Simulate real produce.
	 * @param productName
	 * @param quantity
	 * @return
	 */
	private boolean produce(String productName, int quantity){
		return true;
	}

	/* (non-Javadoc)
	 * @see manufacturer.ManufacturerInterface#processPurchaseOrder(tools.Item)
	 */
	@Override
	public String processPurchaseOrder(Item purchaseItem) throws RemoteException {
		if(!purchaseItem.getManufacturerName().equals(name)){
			System.out.println(name + ": Manufacturer name is not equal to current manufacturer name:" + purchaseItem.getManufacturerName());
			loggerClient.write(name + ": Manufacturer name is not equal to current manufacturer name:" + purchaseItem.getManufacturerName());
			return null;
		}		
		Item availableItem = purchaseOrderManager.itemsMap.get(purchaseItem.getProductType());
		if(availableItem == null){
			loggerClient.write(name + ": " + purchaseItem.getProductType() + " is not supported!");
			return null;
		}else{
			if(purchaseItem.getUnitPrice() < availableItem.getUnitPrice()){
				loggerClient.write(name + ": The order price (" + purchaseItem.getUnitPrice() + ") is lower than defined price(" + availableItem.getUnitPrice() + ")");
				return null;
			}else{
				if(purchaseItem.getQuantity() >= availableItem.getQuantity()){
					int oneTimeQuantity = 100;
					if(produce(purchaseItem.getProductType(), oneTimeQuantity)){
						availableItem.setQuantity(availableItem.getQuantity() + oneTimeQuantity);
						
						purchaseOrderManager.saveItems();
						
						loggerClient.write(name + ": Produced " + oneTimeQuantity + " " + purchaseItem.getProductType());
					}else{
						loggerClient.write(name + ": Failed to produce:" + oneTimeQuantity);
						return null;
					}
				}
				
				if(purchaseItem.getQuantity() >= availableItem.getQuantity()){
					return null;
				}else{
					String orderNumString = new Integer(orderNum++).toString();
					purchaseOrderMap.put(orderNumString, purchaseItem);
					loggerClient.write(name + ": Send order number (" + orderNumString + ") to warehouse.");
					return orderNumString;
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see manufacturer.ManufacturerInterface#getProductInfo(java.lang.String)
	 */
	@Override
	public Product getProductInfo(String productType) throws RemoteException {
		Item avaiableItem = purchaseOrderManager.itemsMap.get(productType);
		if(avaiableItem == null){
			loggerClient.write(name + ": " + productType + " does not exist in this manufacturer!");
			return null;
		}else{
			return new Product(avaiableItem.getManufacturerName(),avaiableItem.getProductType(), avaiableItem.getUnitPrice());
		}
	}
	/* (non-Javadoc)
	 * @see manufacturer.ManufacturerInterface#receivePayment(java.lang.String, float)
	 */
	@Override
	public boolean receivePayment(String orderNum, float totalPrice) throws RemoteException {
		Item waitingForPayItem = purchaseOrderMap.get(orderNum);
		if(waitingForPayItem == null){
			loggerClient.write(name + ": " + orderNum + " does not exist in purchaseOrderMap of current manufacturer!");
			return false;
		}else{
			if(waitingForPayItem.getQuantity() * waitingForPayItem.getUnitPrice() == totalPrice){
				Item inhandItem = purchaseOrderManager.itemsMap.get(waitingForPayItem.getProductType());
				inhandItem.setQuantity(inhandItem.getQuantity() - waitingForPayItem.getQuantity());
				purchaseOrderManager.saveItems();
				loggerClient.write(name + ": received pament. OrderNum:" + orderNum + ", totalPrice:" + totalPrice);
				purchaseOrderMap.remove(orderNum);
				return true;
			}else{
				loggerClient.write(name + ": the total price does not match for this order number: " + orderNum);
				return false;
			}
		}
	}
	
	/**
	 * @return current manufacturer's name
	 */
	public String getName(){
		return name;
	}

	/* (non-Javadoc)
	 * @see manufacturer.ManufacturerInterface#getProductList()
	 */
	@Override
	public ArrayList<Product> getProductList() throws RemoteException {
		ArrayList<Product> productList = new ArrayList<Product>();
		for(Item item: purchaseOrderManager.itemsMap.values()){
			productList.add(item.cloneProduct());
		}
		return productList;
	}
	
	/**
	 * Read product information from configure(xml) file and put them into a items map 
	 */
	private void setProduct(){
		XmlFileController xmlfile = new XmlFileController("settings/product_info.xml");
		Element root = xmlfile.Read();
		if(root != null){
			List<Element> nodes = root.elements("product");
			boolean newProductAdded = false;
			for(Element subElem: nodes){
				String manufacturerName = subElem.element("manufacturerName").getText();
				if(manufacturerName.equals(name)){
					String productType = subElem.element("productType").getText();
					float unitPrice = Float.parseFloat(subElem.element("unitPrice").getText());
					if(purchaseOrderManager.itemsMap.get(productType) == null){
						purchaseOrderManager.itemsMap.put(productType, new Item(manufacturerName, productType, unitPrice, 0));
						newProductAdded = true;
					}
				}
			}
			if(newProductAdded){
				purchaseOrderManager.saveItems();
			}
		}
		
	}
}
