/**
 * 
 */
package tools;

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
import org.dom4j.tree.DefaultElement;

/**
 * @author Ting Zhang
 * @date 2015-05-22
 */
public class PurchaseOrder {
	String manufacturerName;
	String orderNum;
	String customerRef;
	Product product;
	int quantity;
	float unitPrice;
	boolean ifPaid = false;
	
	PurchaseOrder(){
		
	}
	
	PurchaseOrder(String mfn){
		manufacturerName = mfn;
	}
	
	PurchaseOrder(String on, String cr, Product pt, int qt, float up, boolean ip){
		
		orderNum = on;
		customerRef = cr;
		product = pt;
		quantity = qt;
		unitPrice = up;
		ifPaid = ip;
		
	}
	
	public void setManufacturername(String mfn){
		this.manufacturerName = mfn;
	}
	
	public String getOrderNum(){
		return orderNum;
	}
	
	@Override
	/**
	 * Overridden method.
	 * @see Object.toString().
	 */
	public String toString(){
		String payingStatus = "no";
		if(this.ifPaid)
			payingStatus = "yes";
		
		return "manufacturerName:"+this.manufacturerName+"\n"
				+"orderNum: "+this.orderNum+"\n"
				+"customerRef: "+this.customerRef+"\n"
				+"product type: "+this.product.getProductType()+"\n"
				+"quantity: "+this.quantity+"\n"
				+"unit price: "+this.unitPrice+"\n"
				+"paying status: "+payingStatus+"\n"
				;
				
				
	}
	
	/**
	 * save PurchaseOrder into the format of an XML element
	 * 
	 * @return XML element which stores the contents of this purchase order
	 */
	
	public Element saveToElement() {
		//System.out.println("unit price: "+ unitPrice);

		DefaultElement ne = new DefaultElement("order");
		Element on = ne.addElement("orderNum");
		on.setText(orderNum);
		Element cr = ne.addElement("customerRef");
		cr.setText(customerRef);
		Element pt = ne.addElement("productType");
		pt.setText((product).getProductType());
		Element qt = ne.addElement("quantity");
		qt.setText(Integer.toString(quantity));
		Element up = ne.addElement("unitPrice");
		up.setText(Float.toString(unitPrice));
		Element ip = ne.addElement("ifPaid");
		if(this.ifPaid)
			ip.setText("yes");
		else ip.setText("no");
		return ne;
	}
	
	/**
	 * Parse XML element and generate a PurchaseOrder object 
	 * 
	 * @param root 
	 * @return the PurchaseOrder object which is initialized by the given XML element
	*/
	public static PurchaseOrder load(Element root){
		String on;
		String cf;
		Product pt = new Product();
		int qt;
		float up;
		boolean ip;
		on=root.element("orderNum").getText();
		//System.out.println("orderNum:"+ on;)
		cf=root.element("customerRef").getText();
		String typ = root.element("productType").getText();
		pt.setProductType(typ);
		qt= Integer.valueOf(root.element("quantity").getText());
		up = Float.valueOf(root.element("unitPrice").getText());
		String pay = root.element("ifPaid").getText();
		if(pay.equals("yes"))
			ip = true;
		else
			ip = false;
			
		PurchaseOrder ps = new PurchaseOrder(on, cf, pt, qt, up, ip);
		return ps;
	}
}
