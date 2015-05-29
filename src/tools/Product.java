package tools;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 6733918368705678280L;
	protected String manufacturerName;
	protected String productType;
	protected float unitPrice;
	
	public Product(String manufacturerName, String productType, float unitPrice){
		this.manufacturerName = manufacturerName;
		this.productType = productType;
		this.unitPrice = unitPrice;
	}
	
	public Product(Product product){
		this.manufacturerName = product.manufacturerName;
		this.productType = product.productType;
		this.unitPrice = product.unitPrice;
	}
	
	public boolean isSame(Product otherProduct){
		return (manufacturerName == otherProduct.manufacturerName)
				&& (productType == otherProduct.productType)
				&& (unitPrice == otherProduct.unitPrice);
	}
	
	public String toString(){
		return "Manufacturer name:" + manufacturerName
				+ ", Product type:" + productType 
				+ ", Unit price:" + unitPrice;
	}
}
