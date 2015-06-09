package client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import retailer.RetailerInterface;
import tools.Customer;
import tools.Item;
import tools.ItemShippingStatus;
import tools.LoggerClient;
import tools.SignUpResult;

/**
 * @author comp6231.team5
 * Client side application
 * Functionalities: 
 * #sign in
 * #sign up
 * #sign out
 * #get item information
 * #make orders
 */
public class Client {

	private RetailerInterface retailer;
	private Customer currentCustomer;
	private LoggerClient loggerClient;
	private Scanner in;
	private String name;

	/**
	 * Constructor
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Client() throws MalformedURLException, RemoteException, NotBoundException{
		String registryURL = "rmi://localhost:1099/retailer";  
		retailer = (RetailerInterface)Naming.lookup(registryURL); // find the remote object and cast it to an Retailer object
		in = new Scanner(System.in);
		loggerClient = new LoggerClient();
		name = "client"; 
	}

	/**
	 * Sign up
	 * @return sign up result
	 */
	public boolean customerSignUp(){
		if(currentCustomer != null){
			customerSignOut();
		}
		System.out.println("Input customer name:");
		String name = in.next();
		System.out.println("Input customer password:");
		String password = in.next();
		System.out.println("Input customer street1:");
		String street1 = in.next();
		System.out.println("Input customer street2:");
		String street2 = in.next();
		System.out.println("Input customer city:");
		String city = in.next();
		System.out.println("Input customer state:");
		String state = in.next();
		System.out.println("Input customer zip code:");
		String zip = in.next();
		System.out.println("Input customer country:");
		String country = in.next();
		try {
			loggerClient.write(this.name + ": Tries to sign up with:" + name + ", " + password
					+ ", " + street1
					+ ", " + street2
					+ ", " + city
					+ ", " + state
					+ ", " + zip
					+ ", " + country);
			SignUpResult signUpResult  = retailer.signUp(name, password, street1, street2, city, state, zip, country);
			if(signUpResult == null){
				loggerClient.write(this.name + ": The retailer returned null. Failed to signup.");
				System.out.println("The retailer returned null. Failed to signup.");
				return false;
			}else{
				if(signUpResult.result){
					currentCustomer = new Customer(signUpResult.customerReferenceNumber, name, password, street1, street2, city, state, zip, country);
					System.out.println("Your creferenceNumber is:" + signUpResult.customerReferenceNumber);
					loggerClient.write(this.name + ": Sign successfully. The retailer returned:" + signUpResult.message);
				}else{
					System.out.println("Failed to sign up. The retailer returned:" + signUpResult.message);
					loggerClient.write(this.name + ": Failed to sign up. The retailer returned:" + signUpResult.message);
				}
				return signUpResult.result;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Sign in
	 * @return sign in result
	 */
	public boolean customerSignIn(){
		if(currentCustomer != null){
			customerSignOut();
		}
		try{
			System.out.println("Input customer ReferenceNumber:");
			int customerReferenceNumber = Integer.parseInt(in.next());
			System.out.println("Input customer password:");
			String password = in.next();
			try {
				loggerClient.write(name + ": Tries to sign in with reference number:" + customerReferenceNumber + " and password:" + password);
				currentCustomer = retailer.signIn(customerReferenceNumber, password);
				if(currentCustomer == null){
					loggerClient.write(name + ": The retailer returned null. Failed to sign in. Please try again.");
					System.out.println("The retailer returned null. Failed to sign in. Please try again.");
					return false;
				}else{
					System.out.println("Signed in properly. Your person informations are:" + currentCustomer.toString());
					loggerClient.write(name + ": Signed in properly. The customer info:" + currentCustomer.toString());
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				return false;
			}
		} catch (NumberFormatException e){
			System.out.println("ReferenceNumber should contains digits only. Please try again.");
			return false;
		}
	}

	/**
	 * Sing out
	 */
	public void customerSignOut(){
		if(currentCustomer == null)
			return;
		loggerClient.write(name + ": Current customer:" + currentCustomer.getName() + " signed out.");
		currentCustomer = null;
	}

	/**
	 * get retailer's item catalog
	 * @return available item list
	 */
	public ArrayList<Item> getCatalog(){
		if(currentCustomer == null){
			System.out.println("This operation is only allowed for registed user. Please sign in or sign up.");
			return null;
		}else{
			try {
				return retailer.getCatalog(currentCustomer.getCustomerReferenceNumber());
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			} catch (NullPointerException e){
				System.out.println("Catalog is empty.");
				loggerClient.write(name + ": getCatalog return null.");
				return null;
			}
		}
	}

	/**
	 * make order
	 * @return the items shipping status 
	 */
	public ArrayList<ItemShippingStatus> makeOrder(){
		if(currentCustomer == null){
			System.out.println("This operation is only allowed for registed user. Please sign in or sign up.");
			return null;
		}else{
			try {
				ArrayList<Item> itemOrderList = new ArrayList<Item>();
				ArrayList<Item> retailerProductCatalog = retailer.getCatalog(currentCustomer.getCustomerReferenceNumber());
				Random randomGenerator = new Random();
				int randomQuantity;
				Item tmpItem;
				for (Item item : retailerProductCatalog) {
					tmpItem = item;
					randomQuantity = randomGenerator.nextInt(100);
					tmpItem.setQuantity(randomQuantity);
					if(randomQuantity > 0){
						itemOrderList.add(tmpItem);
					}
				}

				try {
					System.out.println("Try to order:");
					loggerClient.write(name + ": Tries to order:");
					String log = new String();
					for(Item item: itemOrderList){
						System.out.println(item.toString());
						log = log + item.toString() + "</br>";
					}
					if(log.length() > 0){
						loggerClient.write(log);
					}
					return retailer.submitOrder(currentCustomer.getCustomerReferenceNumber(), itemOrderList);
				} catch (RemoteException e) {
					e.printStackTrace();
					return null;
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * main provides a menu for user to choose(sign up, sign in, sign out, get catalog, make order and quit) 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client client = new Client();
			String operation;
			do{
				System.out.println("Type [1] to sign up.");
				System.out.println("Type [2] to sign in.");
				System.out.println("Type [3] to sign out.");
				System.out.println("Type [4] to get product catalog.");
				System.out.println("Type [5] to make an order.");
				System.out.println("Type [6] to quit.");
				operation = client.in.next();
				if(operation.compareTo("1") == 0){
					client.customerSignUp();
				}
				else if(operation.compareTo("2") == 0){
					client.customerSignIn();
				}
				else if(operation.compareTo("3") == 0){
					client.customerSignOut();
				}
				else if(operation.compareTo("4") == 0){
					ArrayList<Item> items = client.getCatalog();
					if(items != null){
						System.out.println("Catalog:");
						client.loggerClient.write(client.name + ": The catalog:");
						String log = new String();
						for (Item item : items) {
							System.out.println(item.toString());
							log = log + item.toString() + "</br>";
						}
						if(log.length() > 0){
							client.loggerClient.write(log);
						}
					}
				}
				else if(operation.compareTo("5") == 0){
					ArrayList<ItemShippingStatus> itemshippingStatusList = client.makeOrder();
					if(itemshippingStatusList != null){
						System.out.println("The item shipping status is:");
						client.loggerClient.write(client.name + ": The item shipping status is:");
						String log = new String();
						for (ItemShippingStatus itemShippingStatus : itemshippingStatusList) {
							System.out.println(itemShippingStatus.toString());
							
							log = log + itemShippingStatus.toString() + "</br>";
						}
						if(log.length() > 0){
							client.loggerClient.write(log);
						}
					}
				}
				else if(operation.compareTo("6") == 0){
					break;
				}
				else{
					System.out.println("Wrong input. Try again.");
				}
			}while(true);

			client.in.close();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
