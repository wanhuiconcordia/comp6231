package tools;
import java.io.Serializable;

public class Item extends Product implements Serializable{
	private static final long serialVersionUID = -1927708729616470764L;
	private int quantity;
	
	public Item(String manufacturerName, String productType, float unitPrice, int quantity){
		super(manufacturerName, productType, unitPrice);
		this.quantity = quantity;
	}
	
	public String toString(){
		return super.toString() + ", Quantity:" + quantity; 
	}
}
