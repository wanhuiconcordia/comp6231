package tools;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;




import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

/**
 * 
 */

/**
 * @author Ting Zhang
 * @date 2015-05-20
 */
public class Product implements Serializable {
	

	String manufacturerName = "";
	String productType = "";
    public float unitPrice;
	
	
	Product(Product product){
		
	}
	
	Product(String mfn){
		this.setManufacturerName(mfn);
	}
	
	Product(String mfn, String ptt){
		this.setManufacturerName(mfn);
		this.setProductType(ptt);
	}
	
	Product(String ptt, float up){
		this.setProductType(ptt);
		this.setUnitPrice(up);
	}
	
	public Product(String mfn, String ptt, float up){
		this.setManufacturerName(mfn);
		this.setProductType(ptt);
		this.setUnitPrice(up);
	}
	public Product() {
		// TODO Auto-generated constructor stub
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
	
	public String getProductType(){
		return productType;
	}
	
	public float getUnitPrice(){
		return unitPrice;
	}
	
	@Override
	/**
	 * Overridden method.
	 * @see Object.toString().
	 */
	public String toString(){
		
		return "manufacturerName:"+this.getManufacturerName()+"\n"
				+"productType: "+this.getProductType()+"\n"
				+"unitPrice: "+unitPrice+"\n"
				;
				
				
	}
	
	
	/**
	 * Parse XML element and generate a Product object 
	 * 
	 * @param root
	 *  
	 * @return the Product object which is initialized by the given XML element
	*/
	public static Product load(Element root){
		String productType ="";
		float unitPrice;
		productType=root.element("productType").getText();
		unitPrice = Float.valueOf(root.element("unitPrice").getText());
		
		Product pt = new Product(productType, unitPrice);
		return pt;
	}

}