package tools;
import java.io.Serializable;

public class ItemShippingStatus extends Item implements Serializable{
	private static final long serialVersionUID = -4959224898727923197L;
	private boolean shippingStatus;
	
	public ItemShippingStatus(String manufacturerName, String productType, float unitPrice, int quantity, boolean shippingStatus){
		super(manufacturerName, productType, unitPrice, quantity);
		this.shippingStatus = shippingStatus;
	}
	
	public String toString(){
		return super.toString() + ", Shipping status:" + (shippingStatus ? "shipped" : "not shipped"); 
	}
}
