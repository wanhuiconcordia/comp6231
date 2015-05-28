package retailer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import manufacturer.ManufacturerInterface;


public class WarehouseImplementation extends UnicastRemoteObject implements WarehouseInterface {
	private ManufacturerInterface manufacturer;
	private static final long serialVersionUID = 7663849915764632348L;

	public WarehouseImplementation() throws RemoteException, MalformedURLException, NotBoundException {
		super();
		String registryURL = "rmi://localhost:1099/manufacturer";  
		manufacturer = (ManufacturerInterface)Naming.lookup(registryURL); // find the remote object and cast it to an Retailer object
	}

//	@Override
//	public ItemShippingStatus shipGoods(ArrayList<Item> itemList, Customer customer) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String notifyMe(String message) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void replenish() throws RemoteException{
		System.out.println("Will call manufacturer.processPurchaseOrder");
		manufacturer.processPurchaseOrder("I want to purchase a tv");
	}
}
