package retailer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import manufacturer.ManufacturerInterface;
import tools.Customer;
import tools.Item;
import tools.ItemShippingStatus;


public class WarehouseImplementation extends UnicastRemoteObject implements WarehouseInterface {
	private static final long serialVersionUID = 7663849915764632348L;
	
	private String name;
	
	private ManufacturerInterface manufacturer1;
	private ManufacturerInterface manufacturer2;
	private ManufacturerInterface manufacturer3;

	private ArrayList<Item> inventory;

	
	public WarehouseImplementation() throws RemoteException, MalformedURLException, NotBoundException {
		this("None-name warehouse");
	}
	
	public WarehouseImplementation(String name) throws RemoteException, MalformedURLException, NotBoundException {
		this(name, "localhost", 1099);
	}
	public WarehouseImplementation(String name, String registryHost, int port) throws RemoteException, MalformedURLException, NotBoundException {
		super();
		this.name = name;
		String registryUri1 = "rmi://" + registryHost +"/" + port + "/manufacturer1";
		String registryUri2 = "rmi://" + registryHost +"/" + port + "/manufacturer2";
		String registryUri3 = "rmi://" + registryHost +"/" + port + "/manufacturer3";
//		manufacturer1 = (ManufacturerInterface)Naming.lookup(registryUri1); // find the remote object and cast it to an Retailer object
//		manufacturer2 = (ManufacturerInterface)Naming.lookup(registryUri2);
//		manufacturer3 = (ManufacturerInterface)Naming.lookup(registryUri3);
		inventory = new ArrayList<Item>();
	}

	public ArrayList<Item> shipGoods(final ArrayList<Item> itemList, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notifyMe(String message) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	public void replenish() throws RemoteException{
		System.out.println("Will call manufacturer.processPurchaseOrder");
//		manufacturer1.processPurchaseOrder("I want to purchase a tv");
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Item> getItemList(){
		inventory.add(new Item("manu1", "DVD", 20, 10));
		inventory.add(new Item("manu2", "DVD", 25, 10));
		inventory.add(new Item("manu1", "TV", 100, 10));
		
		return inventory;
	}
}
