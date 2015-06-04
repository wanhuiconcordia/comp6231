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
	int orderNo;

	protected ManufacturerImpl(String name) throws Exception {
		
		super();
		
		this.manuName = name;
		proList = new ProductList();
		proList.loadProducts(name);
		
		purList = new PurchaseOrderList();
		purList.loadPurchaseOrders(name);	
		orderNo = 0;
	}


	private void incrementCounter() {
		orderNo++;		
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
			System.out.println("Came here 1");
			incrementCounter();
			
			order = aPO; 
			order.setOrderNum(this.orderNo);
			
			Map<String, Product> proMap = proList.getProductList();
			
			for (String key : proMap.keySet()) {
				
				System.out.println("Came here 2");
				
				System.out.println(" here key !"+key.toString());
				System.out.println(" prod !"+aPO.getProductType().getProductType());
				   
				   
				   if(key.equalsIgnoreCase(aPO.getProductType().getProductType()))
			       {
					   System.out.println("Came here 3");
					   Product product = proMap.get(key);
					   
					   if(product.getUnitPrice()<= aPO.getProductType().unitPrice)
					   {
						   System.out.println("Came here 4");
						   int quant =  aPO.getQuantity();
						   
						   while (quant > 0)
						   {
							   System.out.println("Came here 5");
							   if(quant <= 100){
								   
								   System.out.println("Came here 6");
								   produce(aPO.getProductType(),aPO.getQuantity());
								   quant = quant - 100;
							   } else
							   {
								   System.out.println("Came here 7");
								   produce(aPO.getProductType(),100);
								   quant = quant - 100;
							   }
						   }
						   
						   return true;
					   
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

	private boolean produce(Product productType, int quantity) {		
		
		if((productType.getProductType().equalsIgnoreCase("DVD player")
		   || productType.getProductType().equalsIgnoreCase("video camera")
		   || productType.getProductType().equalsIgnoreCase("TV"))
		   && quantity <= 100)
		{
			order.setQuantity(order.getQuantity()+quantity);
			purList.getPurchaseOrderList().put(Integer.toString(this.orderNo),this.order);
			try {
				purList.replenish(this.manuName);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
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
