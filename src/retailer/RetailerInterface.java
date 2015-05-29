package retailer;
import java.util.ArrayList;
import java.rmi.Remote;
import java.rmi.RemoteException;

import tools.Customer;
import tools.Item;
import tools.ItemShippingStatus;
import tools.SignUpResult;

public interface RetailerInterface extends Remote{
	
	public ArrayList<Item> getCatalog(int customerReferenceNumber) throws RemoteException;
	
	public ArrayList<ItemShippingStatus> submitOrder(int customerReferenceNumber, ArrayList<Item> itemList) throws RemoteException;
	
	public SignUpResult signUp(String name,
			String password,
			String street1,
			String street2,
			String city,
			String state,
			String zip,
			String country) throws java.rmi.RemoteException;
	
	public Customer signIn(int customerReferenceNumber, String password) throws RemoteException;
}