package retailer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import manufacturer.ManufacturerInterface;
import tools.Item;
import tools.LoggerClient;
import tools.Product;


/**
 * @author comp6231.team5
 *
 */
public class Warehouse {
	private String name;
	
	private HashMap<String, ManufacturerInterface> manufacturerMap;
//	private HashMap<String, Item> inventoryItemMap;
	final int threshold = 100;
	private LoggerClient loggerClient;
	private InventoryManager inventoryManager;
	
	/**
	 * Constructor
	 * @param name
	 * @param loggerClient
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public Warehouse(String name, LoggerClient loggerClient) throws RemoteException, MalformedURLException, NotBoundException {
		this(name, "localhost", 1099, loggerClient);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param registryHost
	 * @param port
	 * @param loggerClient
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public Warehouse(String name, String registryHost, int port, LoggerClient loggerClient) throws RemoteException, MalformedURLException, NotBoundException {
		super();
		this.name = name;
		this.loggerClient = loggerClient;
		String registryUri = "rmi://" + registryHost +":" + port + "/manufacturer";
		manufacturerMap = new HashMap<String, ManufacturerInterface>();
		
		for(int i = 1; i < 4; i++){
			manufacturerMap.put("manufacturer" + i, (ManufacturerInterface)Naming.lookup(registryUri + i));
		}
		
		inventoryManager = new InventoryManager(name); 
		
		for(ManufacturerInterface manufacturer: manufacturerMap.values()){
			ArrayList<Product> productList = manufacturer.getProductList();
			if(productList != null){
				for(Product product: productList){
					String key = product.getManufacturerName() + product.getProductType();
					Item inventoryItem = inventoryManager.inventoryItemMap.get(key);
					if(inventoryItem == null){
						inventoryManager.inventoryItemMap.put(key, new Item(product.clone(), 0));
					}
				}
			}
		}
		replenish();
	}

	/**
	 * shipGoods to client according to the itemList
	 *
	 * @param itemList
	 * @return an ArrayList of items got from current warehouse
	 */
	public ArrayList<Item> shipGoods(final ArrayList<Item> itemList) {
		ArrayList<Item> availableItems = new ArrayList<Item>();
		for(Item item: itemList){
			String key = item.getManufacturerName() + item.getProductType();
			Item inventoryItem = inventoryManager.inventoryItemMap.get(key);
			if(inventoryItem != null){
				if(inventoryItem.getQuantity() < item.getQuantity()){
					availableItems.add(inventoryItem.clone());
					inventoryItem.setQuantity(0);
				}else{
					availableItems.add(item.clone());
					inventoryItem.setQuantity(inventoryItem.getQuantity() - item.getQuantity());
				}
				inventoryManager.saveItems();
			}
		}
		replenish();
		
		String log = new String();
		for(Item item: availableItems){
			log = log + item.toString() + "</br>";
		}
		if(log.length() > 0){
			loggerClient.write(name + ": available items:");
			loggerClient.write(log);
		}
		return availableItems;
	}

	/**
	 * replenish the current warehouse, save the update to the corresponding xml file
	 */
	public void replenish(){
		for(Item item: inventoryManager.inventoryItemMap.values()){
			if(item.getQuantity() < threshold){
				ManufacturerInterface manufacturer = manufacturerMap.get(item.getManufacturerName());
				
				if(manufacturer == null){
					loggerClient.write(name + ": Failed to get manufactorer from manufacturerMap!");
				}else{
					Item orderItem = item.clone();
					
					int oneTimeOrderCount = 40;
					orderItem.setQuantity(oneTimeOrderCount);
					try {
						String orderNum = manufacturer.processPurchaseOrder(orderItem);
						if(orderNum == null){
							loggerClient.write(name + ": manufacturer.processPurchaseOrder return null!");
						}else{
							if(manufacturer.receivePayment(orderNum, orderItem.getUnitPrice() * orderItem.getQuantity())){
								item.setQuantity(item.getQuantity() + oneTimeOrderCount);
								inventoryManager.saveItems();
							}else{
								loggerClient.write(name + ": manufacturer.receivePayment return null!");
							}
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * get the item list for the current warehouse from xml file
	 * @return ArrayList<Item>
	 */
	public ArrayList<Item> getItemList(){
		ArrayList<Item> inventoryItemList = new ArrayList<Item>();
		for(Item item: inventoryManager.inventoryItemMap.values()){
			inventoryItemList.add(item.clone());
		}
		
		return inventoryItemList;
	}
}
