package retailer;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import tools.Customer;
import tools.CustomerManager;
import tools.Item;
import tools.ItemShippingStatus;
import tools.SignUpResult;


public class RetailerImplimentation extends UnicastRemoteObject implements RetailerInterface {

	private static final long serialVersionUID = -3984384512642303801L;
	private static final int warehouseCount = 3;
	private WarehouseImplementation []warehouses;

	private CustomerManager customerManager;
	public RetailerImplimentation() throws RemoteException, MalformedURLException, NotBoundException {
		super();
		warehouses = new WarehouseImplementation[warehouseCount];
		String warehouseName = "Warehouse";
		for(int i = 0; i < warehouses.length; i++){
			String currentWarehouseName = warehouseName + i;
			warehouses[i] = new WarehouseImplementation(currentWarehouseName);
		}

		customerManager = new CustomerManager();
	}

	@Override
	public synchronized ArrayList<Item> getCatalog(int customerReferenceNumber) throws RemoteException {
		System.out.println("got a getCatalog event:");
		ArrayList<Item> allItems = new ArrayList<Item>();

		for(int i = 0; i < warehouses.length; i++){
			ArrayList<Item> itemListFromWarehouse = warehouses[i].getItemList();
			allItems = mergeItems(allItems, itemListFromWarehouse);
		}

		return allItems;
	}

	@Override
	public ArrayList<ItemShippingStatus> submitOrder(
			int customerReferenceNumber, ArrayList<Item> orderItemList)
					throws RemoteException {
		System.out.println("got a submitOrder");
		Customer currentCustomer = customerManager.getCustomerByReferenceNumber(customerReferenceNumber);
		if(currentCustomer == null){
			return null;
		}else if(orderItemList == null){
			return null;
		}else if(orderItemList.size() == 0){
			return null;
		}else{
			int []randomOrder = getRandOrder(warehouses.length);
			ArrayList<ItemShippingStatus> itemShippingStatusList = new ArrayList<ItemShippingStatus>();
			ArrayList<Item> itemsGotFromCurrentWarehouse;
			for(int currentWarehouseIndex: randomOrder){
				itemsGotFromCurrentWarehouse = warehouses[currentWarehouseIndex].shipGoods(orderItemList, currentCustomer);
				if(itemsGotFromCurrentWarehouse != null){
					//add itemsGotFromCurrentWarehouse to itemShippingStatusList
					for(Item item: itemsGotFromCurrentWarehouse){
						boolean isItemAddedInShipingList = false;
						for(ItemShippingStatus itemShippingStatus: itemShippingStatusList){
							if(itemShippingStatus.isSameProductAs(item)){
								isItemAddedInShipingList = true;
								itemShippingStatus.setQuantity(item.getQuantity() + itemShippingStatus.getQuantity());
							}
						}
						
						if(!isItemAddedInShipingList){
							itemShippingStatusList.add(new ItemShippingStatus(item, true));
						}
					}
					//subtract items of itemsGotFromCurrentWarehouse from orderItemList
					
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
			
			if(!orderItemList.isEmpty()){
				for(Item item: orderItemList){
					itemShippingStatusList.add(new ItemShippingStatus(item, false));
				}
			}
			return itemShippingStatusList;
		}
	}

	@Override
	public SignUpResult signUp(String name, String password, String street1,
			String street2, String city, String state, String zip,
			String country) throws RemoteException {
		System.out.println("got a signup event");
		return customerManager.register(name, password, street1, street2, city, state, zip, country);
	}

	@Override
	public Customer signIn(int customerReferenceNumber, String password) throws RemoteException {
		System.out.println("got a signin event");


		return customerManager.find(customerReferenceNumber, password);
		//return  null;

	}

	private ArrayList<Item> mergeItems(ArrayList<Item> itemList1, ArrayList<Item> itemList2){
		ArrayList<Item> allItems = new ArrayList<Item>();
		if(itemList1 != null){
			allItems.addAll(itemList1);
		}

		if(itemList2 != null){

			for(Item currentItem: itemList2){
				boolean existFlag = false;
				for(Item item: allItems){
					if(currentItem.isSameProductAs(item)){
						item.setQuantity(item.getQuantity() + currentItem.getQuantity());
						existFlag = true;
					}
				}
				if(!existFlag){
					allItems.add(currentItem);
				}
			}
		}
		return allItems;
	}

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
