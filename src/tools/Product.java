package tools;
import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 6733918368705678280L;
	private String manufacturerName;
	private String productType;
	private float unitPrice;
	
	public Product(String manufacturerName, String productType, float unitPrice){
		this.manufacturerName = manufacturerName;
		this.productType = productType;
		this.unitPrice = unitPrice;
	}
	public String toString(){
		return "Manufacturer name:" + manufacturerName
				+ ", Product type:" + productType 
				+ ", Unit price:" + unitPrice;
	}
}
