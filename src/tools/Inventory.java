/**
 * 
 */
package tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.tree.DefaultElement;

/**
 * @author Ting Zhang
 * @date 2015-05-24
 */
public class Inventory {
	String manufacturerName;
	String productType;
	float unitPrice;
	int quantity;
	
	Inventory(){
		
	}
	
	public Inventory(String mfn, String typ, Float up, int qt){
		manufacturerName=mfn;
		productType = typ;
		unitPrice = up;
		quantity = qt;
	}
	
	public void setQuantity(int newQt){
		this.quantity = newQt;
	}
	
	@Override
	/**
	 * Overridden method.
	 * @see Object.toString().
	 */
	
	public String toString(){
		
		
		return "manufacturerName:"+this.manufacturerName+"\n"
				
				+"product type: "+this.productType+"\n"
				+"unit price: "+this.unitPrice+"\n"
				+"quantity: "+this.quantity+"\n"
				;
				
				
	}
	
	/**
	 * save a inventory record into the format of an XML element
	 * 
	 * @return XML element which stores the contents of this inventory record
	 */
	
	public Element saveToElement() {
		//System.out.println("unit price: "+ unitPrice);

		DefaultElement ne = new DefaultElement("record");
		Element on = ne.addElement("manufacturerName");
		on.setText(manufacturerName);
		Element cr = ne.addElement("productType");
		cr.setText(productType);
		Element up = ne.addElement("unitPrice");
		up.setText(Float.toString(unitPrice));
		Element qt = ne.addElement("quantity");
		qt.setText(Integer.toString(quantity));
		
		
		return ne;
	}
	
	/**
	 * Parse XML element and generate a Inventory object 
	 * 
	 * @param root 
	*/
	public static Inventory loadItem(Element root){
		String mfn="";
		String typ="";
		float up;
		int qt;
		mfn=root.element("manufacturerName").getText();
		typ=root.element("productType").getText();
		up = Float.valueOf(root.element("unitPrice").getText());
		qt= Integer.valueOf(root.element("quantity").getText());
		Inventory it = new Inventory(mfn,typ,up,qt);
		return it;
	}
	
	
}
