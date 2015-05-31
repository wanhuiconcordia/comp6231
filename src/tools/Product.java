package tools;

import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

public class Product implements Serializable {
	private static final long serialVersionUID = 6733918368705678280L;
	protected String manufacturerName;
	protected String productType;
	protected float unitPrice;
	
	public Product(){
		
	}

	Product(String mfn){
		this.manufacturerName = mfn;
	}
	
	Product(String mfn, String ptt){
		this.manufacturerName = mfn;
		this.productType = ptt;
	}
	
	Product(String ptt, float up){
		this.setProductType(ptt);
		this.setUnitPrice(up);
	}
	
	public void setManufacturerName(String newmfn){
		manufacturerName = newmfn;
	}
	
	public void setProductType(String pt){
		productType = pt;
	}
	
	public void setUnitPrice(float up){
		unitPrice = up;
	}
	
	public String getManufacturerName(){
		return manufacturerName;
	}
	
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
	
	public String getProductType(){
		return productType;
	}
	
	public static Product load(Element root){
		String productType ="";
		float unitPrice;
		productType=root.element("productType").getText();
		unitPrice = Float.valueOf(root.element("unitPrice").getText());
		
		Product pt = new Product(productType, unitPrice);
		return pt;
	}
}
