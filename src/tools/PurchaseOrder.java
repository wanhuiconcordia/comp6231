/**
 * 
 */
package tools;

import java.io.Serializable;
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
public class PurchaseOrder implements Serializable {
	String manufacturerName;
	int orderNum = 0;
	String customerRef;
	Product product;
	int quantity;
	float unitPrice;
	float totalPrice;
	boolean ifPaid = false;
	
	public PurchaseOrder(){
		
	}
	
	PurchaseOrder(String mfn){
		manufacturerName = mfn;
	}
	
	public PurchaseOrder(int no,String cr, Product pt, int qt, float up, float tp, boolean pa){
		
		orderNum = no;
		customerRef = cr;
		product = pt;
		quantity = qt;
		unitPrice = up;
		totalPrice = tp;
		ifPaid = pa ;
		
	}
	
	public Product getProductType(){
		return product;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setManufacturername(String mfn){
		this.manufacturerName = mfn;
	}
	
	public void setPaymentStatus(boolean paid){
		this.ifPaid = paid;
	}
	
	public void setOrderNum(int no){
		this.orderNum = no;
	}
	
	public int getOrderNum(){
		return orderNum;
	}
	
	public float getTotalPrice(){
		return totalPrice;
	}
	
	public boolean getPaidStatus(){
		return ifPaid;
	}
	
	public void setQuantity(int quan){
		this.quantity = quan;
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
				+"total price: "+this.totalPrice+"\n"
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
		on.setText(String.valueOf(orderNum));
		Element cr = ne.addElement("customerRef");
		cr.setText(customerRef);
		Element pt = ne.addElement("productType");
		pt.setText((product).getProductType());
		Element qt = ne.addElement("quantity");
		qt.setText(Integer.toString(quantity));
		Element up = ne.addElement("unitPrice");
		up.setText(Float.toString(unitPrice));
		Element tp = ne.addElement("totalPrice");
		tp.setText(Float.toString(totalPrice));
		Element pa = ne.addElement("ifPaid");
		pa.setText(Boolean.toString(ifPaid));
		
		return ne;
	}
	
	/**
	 * Parse XML element and generate a PurchaseOrder object 
	 * 
	 * @param root 
	 * @return the PurchaseOrder object which is initialized by the given XML element
	*/
	public static PurchaseOrder load(Element root){
		int on;
		String cf;
		Product pt = new Product();
		int qt;
		float up;
		float tp;
		boolean pa;
		on=Integer.valueOf(root.element("orderNum").getText());
		//System.out.println("orderNum:"+ on;)
		cf=root.element("customerRef").getText();
		String typ = root.element("productType").getText();
		pt.setProductType(typ);
		qt= Integer.valueOf(root.element("quantity").getText());
		up = Float.valueOf(root.element("unitPrice").getText());
		tp = Float.valueOf(root.element("totalPrice").getText());
		pa = Boolean.valueOf(root.element("ifPaid").getText());
		
		PurchaseOrder ps = new PurchaseOrder(on, cf, pt, qt, up, tp,pa);
		return ps;
	}
	
	
	
	

}
