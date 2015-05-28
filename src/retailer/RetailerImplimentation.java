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

	private WarehouseImplementation wareHouse;
	public RetailerImplimentation() throws RemoteException, MalformedURLException, NotBoundException {
		super();
//		wareHouse = new WarehouseImplementation();
	}
	
	@Override
	public ArrayList<Product> getCatalog(int customerReferenceNumber) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("got a getCatalog event");
		return null;
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
		
		// TODO
		// if(name exists in database){
		//		return new SignUpResult(false, -1, "Name exists");		
		// }else{
		// 		generate a random customerReferrenceNumber which is not in database
		System.out.println("got a signup event");
		System.out.println("will call warehouse.replenish()");
//		wareHouse.replenish();
		
		return new SignUpResult(true, 12345, "Sign up properly.");
		//}
	}

	@Override
	public Customer signIn(int customerReferenceNumber, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("got a signin event");
		return null;
	}

}
