package retailer;
import tools.Customer;
import tools.Item;
import tools.ItemShippingStatus;
import tools.Product;
import tools.SignUpResult;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class RetailerImplimentation extends UnicastRemoteObject implements RetailerInterface {

	private static final long serialVersionUID = -3984384512642303801L;

	private WarehouseImplementation warehouse1;
	private WarehouseImplementation warehouse2;
	private WarehouseImplementation warehouse3;
	public RetailerImplimentation() throws RemoteException, MalformedURLException, NotBoundException {
		super();
//		warehouse1 = new WarehouseImplementation("Warehouse1");
//		warehouse2 = new WarehouseImplementation("Warehouse2");
//		warehouse3 = new WarehouseImplementation("Warehouse3");
	}
	
	@Override
	public ArrayList<Item> getCatalog(int customerReferenceNumber) throws RemoteException {
		ArrayList<Item> allItems = new ArrayList<Item>();
		ArrayList<Item> itemList1 = warehouse1.getItemList();
		ArrayList<Item> itemList2 = warehouse2.getItemList();
		ArrayList<Item> itemList3 = warehouse3.getItemList();

		allItems = mergeItems(allItems, itemList1);
		allItems = mergeItems(allItems, itemList2);
		allItems = mergeItems(allItems, itemList3);
		
		return allItems;
	}

	@Override
	public ArrayList<ItemShippingStatus> submitOrder(
			int customerReferenceNumber, ArrayList<Item> itemList)
			throws RemoteException {
		System.out.println("got a submitOrder event");
		//TODO 
		/*
		 * 1. 
		 */
		return null;
	}

	@Override
	public SignUpResult signUp(String name, String password, String street1,
			String street2, String city, String state, String zip,
			String country) throws RemoteException {
		System.out.println("got a signup event");
		//TODO return customerManager.register(name, password, street1, street2, city, state, zip, country);
		return new SignUpResult(true, 1111, "good");
	}

	@Override
	public Customer signIn(int customerReferenceNumber, String password) throws RemoteException {
		System.out.println("got a signin event");
		//TODO return customerManager.find(customerReferenceNumber, password);
		return  null;
				
	}
	
	private ArrayList<Item> mergeItems(ArrayList<Item> itemList1, ArrayList<Item> itemList2){
		ArrayList<Item> allItems = new ArrayList<Item>();
		if(itemList1 != null){
			allItems.addAll(itemList1);
		}
		
		if(itemList2 != null){
			Item currentItem;
			while(!itemList2.isEmpty()){
				currentItem = itemList2.remove(0);
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
