package retailer;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import tools.Customer;
import tools.CustomerManager;
import tools.Item;
import tools.ItemShippingStatus;
import tools.SignUpResult;


public class RetailerImplimentation extends UnicastRemoteObject implements RetailerInterface {

	private static final long serialVersionUID = -3984384512642303801L;

	private WarehouseImplementation []warehouse;
	
	private CustomerManager customerManager;
	public RetailerImplimentation() throws RemoteException, MalformedURLException, NotBoundException {
		super();
		warehouse = new WarehouseImplementation[3];
		warehouse[0] = new WarehouseImplementation("Warehouse1");
		warehouse[1] = new WarehouseImplementation("Warehouse2");
		warehouse[2] = new WarehouseImplementation("Warehouse3");
		customerManager = new CustomerManager();
	}

	@Override
	public synchronized ArrayList<Item> getCatalog(int customerReferenceNumber) throws RemoteException {
		System.out.println("got a getCatalog event:");
		ArrayList<Item> allItems = new ArrayList<Item>();
		ArrayList<Item> itemList1 = warehouse[0].getItemList();
		ArrayList<Item> itemList2 = warehouse[1].getItemList();
		ArrayList<Item> itemList3 = warehouse[2].getItemList();
		
		allItems = mergeItems(allItems, itemList1);
		allItems = mergeItems(allItems, itemList2);
		allItems = mergeItems(allItems, itemList3);
		return allItems;
	}

	@Override
	public ArrayList<ItemShippingStatus> submitOrder(
			int customerReferenceNumber, ArrayList<Item> itemList)
					throws RemoteException {
		System.out.println("got a submitOrder");
		if(customerManager.find(customerReferenceNumber)){
			//			System.out.println("Client order list:");
			//			for(Item item: itemList){
			//				System.out.println(item.toString());
			//			}
			
			/*
			 * loop through the items in itemList
			 * 
			 * take currentItem.quantity
			 */
			
			for(Item item: itemList){
				
			}
			return null;
		}else{
			return null;
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
}
