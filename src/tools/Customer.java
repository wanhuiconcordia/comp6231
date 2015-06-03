package tools;
import java.io.Serializable;
public class Customer implements Serializable{
	private static final long serialVersionUID = -6031592301495856489L;
	private int customerReferenceNumber;
	private String name;
	private String password;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String country;

	public Customer(int customerReferenceNumber,
			String name,
			String password,
			String street1,
			String street2,
			String city,
			String state,
			String zip,
			String country){
		this.customerReferenceNumber = customerReferenceNumber;
		this.name = name;
		this.password = password;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
	
	public String toString(){
		return "CustomerReferenceNumber:" + customerReferenceNumber 
				+ ", Name:" + name
				+ ", Password:" + password
				+ ", Street1:" + street1
				+ ", Street2:" + street2
				+ ", City:" + city
				+ ", State:" + state
				+ ", Zip:" + zip
				+ ", Country:" + country;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public int getCustomerReferenceNumber(){
		return customerReferenceNumber;
	}
}
