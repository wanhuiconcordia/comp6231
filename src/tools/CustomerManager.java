package tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CustomerManager {

	private ArrayList<Customer> customers;

	public CustomerManager(){
		customers = new ArrayList<Customer>();
		loadCustomers();
	}

	public void saveCustomers()
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream("customers.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(customers);
			out.close();
			fileOut.close();

		}catch(IOException i){
			i.printStackTrace();
		}
	}

	public void loadCustomers()
	{
		try
		{
			FileInputStream fileIn = new FileInputStream("customers.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			customers = (ArrayList<Customer>) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i){
//			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			c.printStackTrace();

			return;
		}
	}

	public synchronized SignUpResult register(String name, String password, String street1, String street2, String city, String state, String zip, String country){
		for(Customer customer: customers){
			if(customer.getName().equals(name) && customer.getPassword().equals(password)){
				return new SignUpResult(false, -1, "Failed to sign up! (User name exists, try another name)");
			}
		}
		int customerReferenceNumber = 1000 + customers.size();
		customers.add(new Customer(customerReferenceNumber, name, password, street1, street2, city, state, zip, country));
		System.out.println("Will save this user:");
		saveCustomers();
		return new SignUpResult(true, customerReferenceNumber , "Sign up successfully.");
	}

	public synchronized Customer find(int customerReferenceNumber, String password){
		for(Customer customer: customers){
			if(customer.getCustomerReferenceNumber() == customerReferenceNumber
					&& customer.getPassword().equals(password)){
				return customer;
			}
		}
		return null;
	}
	
	public synchronized boolean find(int customerReferenceNumber){
		for(Customer customer: customers){
			if(customer.getCustomerReferenceNumber() == customerReferenceNumber){
				return true;
			}
		}
		return false;
	}
	
	public synchronized Customer getCustomerByReferenceNumber(int customerReferenceNumber){
		for(Customer customer: customers){
			if(customer.getCustomerReferenceNumber() == customerReferenceNumber){
				return customer;
			}
		}
		return null;
	}
}
