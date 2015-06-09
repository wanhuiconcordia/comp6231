package retailer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import tools.Customer;
import tools.Item;
import tools.ItemShippingStatus;
import tools.SignUpResult;

public interface RetailerInterface extends Remote{
	
	/**
	 * get the product catalog for a customer
	 * @param customerReferenceNumber
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<Item> getCatalog(int customerReferenceNumber) throws RemoteException;
	
	/**
	 * submit a cumtomer's order
	 * @param customerReferenceNumber
	 * @param itemList
	 * @return an Arraylist of ItemShippingStatus
	 * @throws RemoteException
	 */
	public ArrayList<ItemShippingStatus> submitOrder(int customerReferenceNumber, ArrayList<Item> itemList) throws RemoteException;
	
	/**
	 * sign up a customer according to the info provided
	 * @param name
	 * @param password
	 * @param street1
	 * @param street2
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return SignUpResult object 
	 * @throws java.rmi.RemoteException
	 */
	public SignUpResult signUp(String name,
			String password,
			String street1,
			String street2,
			String city,
			String state,
			String zip,
			String country) throws java.rmi.RemoteException;
	
	/**
	 * sign a customer according to the customerReferenceNumbe and password
	 * @param customerReferenceNumber
	 * @param password
	 * @return the customer object
	 * @throws RemoteException
	 */
	public Customer signIn(int customerReferenceNumber, String password) throws RemoteException;
}