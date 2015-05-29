package tools;
import java.io.Serializable;

public class Item extends Product implements Serializable{
	private static final long serialVersionUID = -1927708729616470764L;
	private int quantity;
	
	public Item(String manufacturerName, String productType, float unitPrice, int quantity){
		super(manufacturerName, productType, unitPrice);
		this.quantity = quantity;
	}
	
	public Item(Product product, int quantity){
		super(product);
		this.quantity = quantity;
	}
	
	public String toString(){
		return super.toString() + ", Quantity:" + quantity; 
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int q){
		quantity = q;
	}
	
	public boolean isSameProductAs(Item otherItem){
		return (this.manufacturerName == otherItem.manufacturerName)
				&& (this.productType == otherItem.productType)
				&& (this.unitPrice == otherItem.unitPrice);
	}
}
