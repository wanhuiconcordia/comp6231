package retailer;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import tools.Customer;
import tools.Item;
import tools.ItemShippingStatus;
import tools.LoggerClient;
import tools.SignUpResult;


/**
 * @author comp6231.team5
 *
 */
public class RetailerImplimentation extends UnicastRemoteObject implements RetailerInterface {

	private static final long serialVersionUID = -3984384512642303801L;
	private static final int warehouseCount = 3;
	private Warehouse []warehouses;
	private LoggerClient loggerClient;
	private CustomerManager customerManager;
	private String name;
	public RetailerImplimentation(LoggerClient loggerClient) throws RemoteException, MalformedURLException, NotBoundException {
		super();
		name = "RetailerImplimentation";
		this.loggerClient = loggerClient;
		warehouses = new Warehouse[warehouseCount];
		String warehouseName = "Warehouse";
		for(int i = 1; i <= warehouses.length; i++){
			String currentWarehouseName = warehouseName + i;
			warehouses[i - 1] = new Warehouse(currentWarehouseName, loggerClient);
		}

		customerManager = new CustomerManager("customers.xml");
	}

	/* (non-Javadoc)
	 * @see retailer.RetailerInterface#getCatalog(int)
	 */
	@Override
	public synchronized ArrayList<Item> getCatalog(int customerReferenceNumber) throws RemoteException {
		ArrayList<Item> allItems = new ArrayList<Item>();
		HashMap<String, Item> itemsMap = new HashMap<String, Item>();

		for(int i = 0; i < warehouses.length; i++){
			ArrayList<Item> itemListFromWarehouse = warehouses[i].getItemList();
			for(Item item: itemListFromWarehouse){
				String key = item.getManufacturerName() + item.getProductType();
				Item itemInMap = itemsMap.get(key); 
				if(itemInMap == null){
					itemsMap.put(key, item.clone());
				}else{
					itemInMap.setQuantity(itemInMap.getQuantity() + item.getQuantity());
				}
			}
		}

		for(Item item: itemsMap.values()){
			allItems.add(item);
		}
		return allItems;
	}

	/* (non-Javadoc)
	 * @see retailer.RetailerInterface#submitOrder(int, java.util.ArrayList)
	 */
	@Override
	public ArrayList<ItemShippingStatus> submitOrder(int customerReferenceNumber, ArrayList<Item> orderItemList) throws RemoteException {
		Customer currentCustomer = customerManager.getCustomerByReferenceNumber(customerReferenceNumber);
		if(currentCustomer == null){
			loggerClient.write(name + ": customer reference number can not be found in customer database.");
			return null;
		}else if(orderItemList == null){
			loggerClient.write(name + ": null order list.");
			return null;
		}else if(orderItemList.size() == 0){
			loggerClient.write(name + ": empty order list.");
			return null;
		}else{
			HashMap<String, ItemShippingStatus> itemShippingStatusMap = new HashMap<String, ItemShippingStatus>(); 
			int []randomOrder = getRandOrder(warehouses.length);
			ArrayList<Item> itemsGotFromCurrentWarehouse;
			for(int currentWarehouseIndex: randomOrder){
				itemsGotFromCurrentWarehouse = warehouses[currentWarehouseIndex].shipGoods(orderItemList);
				
				if(itemsGotFromCurrentWarehouse != null){
					loggerClient.write(name + ": Items got from " + warehouses[currentWarehouseIndex].getName());
					String log = new String();
					for(Item item: itemsGotFromCurrentWarehouse){
						//System.out.println(item.toString());
						log = log + item.toString() + "</br>";
					}
					
					if(log.length() > 0){
						loggerClient.write(log);
					}
					
					for(Item item: itemsGotFromCurrentWarehouse){
						String key = item.getManufacturerName() + item.getProductType();
						ItemShippingStatus itemShippingStatus = itemShippingStatusMap.get(key);
						if(itemShippingStatus == null){
							itemShippingStatusMap.put(key, new ItemShippingStatus(item.clone(), true));
						}else{
							itemShippingStatus.setQuantity(itemShippingStatus.getQuantity() + item.getQuantity());
						}
					}
					

					for (int i = 0; i < orderItemList.size();) {
						Item item = orderItemList.get(i);
						for(Item item_t: itemsGotFromCurrentWarehouse){
							if(item.isSameProductAs(item_t)){
								item.setQuantity(item.getQuantity() - item_t.getQuantity());
							}
						}
						if(item.getQuantity() == 0){
							orderItemList.remove(i);
						}else{
							i++;
						}
					}
				}					

				//if no more item in orderItemList break;
				if(orderItemList.isEmpty()){
					break;
				}
			}
			//if there are still some items in orderItemList, put them in to inhandItemShippingStatusList and mark their shippingStatus as false

			ArrayList<ItemShippingStatus> itemShippingStatusList = new ArrayList<ItemShippingStatus>();
			for(ItemShippingStatus itemShippingStatus: itemShippingStatusMap.values()){
				itemShippingStatusList.add(itemShippingStatus);
			}
			
			if(!orderItemList.isEmpty()){
				for(Item item: orderItemList){
					itemShippingStatusList.add(new ItemShippingStatus(item, false));
				}
			}
			
			String log = new String();
			for(ItemShippingStatus itemShippingStatus: itemShippingStatusList){
				log = log + itemShippingStatus.toString() + "</br>";
			}
			if(log.length() > 0){
				loggerClient.write(name + ": item shipping status will be sent to client.");
				loggerClient.write(log);
			}
			return itemShippingStatusList;
		}
	}

	/* (non-Javadoc)
	 * @see retailer.RetailerInterface#signUp(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SignUpResult signUp(String name, String password, String street1,
			String street2, String city, String state, String zip,
			String country) throws RemoteException {
		return customerManager.register(name, password, street1, street2, city, state, zip, country);
	}

	/* (non-Javadoc)
	 * @see retailer.RetailerInterface#signIn(int, java.lang.String)
	 */
	@Override
	public Customer signIn(int customerReferenceNumber, String password) throws RemoteException {
		return customerManager.find(customerReferenceNumber, password);
	}

	/**
	 * get a random order for number between 1 to count
	 * @param count
	 * @return int[] which stores the randomized number from 1 to count
	 */
	private int[] getRandOrder(int count){
		int[][] orderContainer = new int[count][2];
		Random randomGenerator = new Random();
		for(int i = 0; i < count; i++){
			orderContainer[i][0] = i;
			orderContainer[i][1] = randomGenerator.nextInt(count * 2);
		}


		for(int i = 0; i < count - 1; i++){
			for(int j = i + 1; j < count; j++){
				if(orderContainer[i][1] > orderContainer[j][1]){
					int tmp = orderContainer[i][1];
					orderContainer[i][1] = orderContainer[j][1];
					orderContainer[j][1] = tmp;

					tmp = orderContainer[i][0];
					orderContainer[i][0] = orderContainer[j][0];
					orderContainer[j][0] = tmp;
				}
			}
		}
		int []order = new int[count];
		for(int i = 0; i < count; i++){
			order[i] = orderContainer[i][0];
		}
		return order;
	}
}
