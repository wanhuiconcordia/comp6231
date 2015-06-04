/**
 * 
 */
package manufacturer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Map;

import tools.Product;
import tools.ProductList;
import tools.PurchaseOrder;
import tools.PurchaseOrderList;

/**
 * @author Ratzzz
 *
 */
public class ManufacturerImpl extends UnicastRemoteObject implements ManufacturerInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2318805458513723930L;
	PurchaseOrder order;
	ProductList proList;
	PurchaseOrderList purList;
	String manuName;

	protected ManufacturerImpl(String name) throws Exception {
		
		super();
		
		this.manuName = name;
		proList = new ProductList();
		proList.loadProducts(name);
		
		purList = new PurchaseOrderList();
		purList.loadPurchaseOrders(name);
	}


	@Override
	public boolean isLoginValid(String name) throws RemoteException {
		
		if(name.equalsIgnoreCase("ME"))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean processPurchaseOrder(PurchaseOrder aPO)
			throws RemoteException {
		try
		{
			Map<String, Product> proMap = proList.getProductList();
			
			for (String key : proMap.keySet()) {
				   
				   if(key.equalsIgnoreCase(aPO.getProductType().toString()))
			       {
					   Product product = proMap.get(key);
					   
					   if(product.getUnitPrice()<= aPO.getProductType().unitPrice)
					   {
						   aPO.getQuantity();
						   return produce(aPO);
					   
					   }else
					   {
						   return false;
					   }  
					 	   
			       }
			}			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	private boolean produce(PurchaseOrder aPO) {
		
		purList.getPurchaseOrderList().put("5", aPO);
		try {
			purList.replenish(this.manuName);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}


	/**
	 * Load from productList  the product  with the specified productType 
	 * 
	 * @param product type
	 * 
	 * @return Product
	 * 
	 * @throws Exception 
	 */
	public Product getProductInfo(String typ) throws RemoteException 
	{
		try {
			return proList.getProductInfo(typ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;				
	}

	@Override
	public boolean receivePayment(String orderNum, float totalPrice)
			throws RemoteException {
		try
		{
		
		Map<String, PurchaseOrder> ordMap = purList.getPurchaseOrderList();
		
		for (String key : ordMap.keySet()) {
			   
			   if(key.equalsIgnoreCase(orderNum))
		       {
				   PurchaseOrder value = ordMap.get(key);
				   
				  if( value.getTotalPrice() == totalPrice && value.getPaidStatus()!= true)
				  {
					  value.setPaymentStatus(true);
					  
					  purList.getPurchaseOrderList().put(orderNum, value);
					  purList.replenish(this.manuName);					  
					  return true;
				  }					  
				  else				  
					  return false;			   
		       }
			   return false;
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

}
