package retailer;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import tools.Customer;
import tools.SignUpResult;
import tools.XmlFileController;

/**
 * @author comp6231.team5
 *
 */
public class CustomerManager {

	private ArrayList<Customer> customers;

	private String fileName;

	/**
	 * constructor
	 * @param fileName
	 */
	public CustomerManager(String fileName){
		customers = new ArrayList<Customer>();
		this.fileName = fileName;
		loadCustomers();
	}

	/**
	 * save customers to xml file
	 */
	public void saveCustomers()
	{
		XmlFileController xmlFileControler = new XmlFileController(fileName);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("customers");
		for(Customer customer: customers){
			Element ml = customer.toXmlElement();
			root.add(ml);
		}
		try {
			xmlFileControler.Write(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * load customers from xml file
	 */
	public void loadCustomers()
	{
		XmlFileController xmlfile = new XmlFileController(fileName);
		Element root = xmlfile.Read();
		if(root != null){
			List<Element> nodes = root.elements("customer");
			for(Element subElem: nodes){
				int customerReferenceNumber = Integer.parseInt(subElem.element("customerReferenceNumber").getText());
				String name = subElem.element("name").getText();
				String password = subElem.element("password").getText(); 
				String street1 = subElem.element("street1").getText();
				String street2 = subElem.element("street2").getText();
				String city = subElem.element("city").getText();
				String state = subElem.element("state").getText();
				String zip = subElem.element("zip").getText();
				String country = subElem.element("country").getText();
				customers.add(new Customer(customerReferenceNumber, name, password, street1, street2, city, state, zip, country));
			}
		}
	}

	/**
	 * register a customer, if succeed, add this customer to the customers
	 * and save this customers to file
	 * @param name
	 * @param password
	 * @param street1
	 * @param street2
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return SignUpResult object 
	 */
	public synchronized SignUpResult register(String name, String password, String street1, String street2, String city, String state, String zip, String country){
		for(Customer customer: customers){
			if(customer.getName().equals(name) && customer.getPassword().equals(password)){
				return new SignUpResult(false, -1, "Failed to sign up! (User name exists, try another name)");
			}
		}
		int customerReferenceNumber = 1000 + customers.size();
		customers.add(new Customer(customerReferenceNumber, name, password, street1, street2, city, state, zip, country));
		saveCustomers();
		return new SignUpResult(true, customerReferenceNumber , "Sign up successfully.");
	}

	/**
	 * find a customer according to the customerReferenceNumber and password
	 * @param customerReferenceNumber
	 * @param password
	 * @return the customer found
	 */
	public synchronized Customer find(int customerReferenceNumber, String password){
		for(Customer customer: customers){
			if(customer.getCustomerReferenceNumber() == customerReferenceNumber
					&& customer.getPassword().equals(password)){
				return customer;
			}
		}
		return null;
	}

	/**
	 * find a customer according to the customerReferenceNumber
	 * @param customerReferenceNumber
	 * @return ture if find, false if not
	 */
	public synchronized boolean find(int customerReferenceNumber){
		for(Customer customer: customers){
			if(customer.getCustomerReferenceNumber() == customerReferenceNumber){
				return true;
			}
		}
		return false;
	}

	/**
	 * get a customer for the customerReferenceNumber
	 * @param customerReferenceNumber
	 * @return customer
	 */
	public synchronized Customer getCustomerByReferenceNumber(int customerReferenceNumber){
		for(Customer customer: customers){
			if(customer.getCustomerReferenceNumber() == customerReferenceNumber){
				return customer;
			}
		}
		return null;
	}
}
