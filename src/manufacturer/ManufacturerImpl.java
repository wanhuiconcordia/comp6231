/**
 * 
 */
package manufacturer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Map;

import tools.LoggerClient;
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
	LoggerClient client;
	String manuName;
	int orderNo;
	int count = 0;

	protected ManufacturerImpl(String name) throws Exception {
		
		super();
		
		this.manuName = name;
		proList = new ProductList();
		proList.loadProducts(name);
		
		purList = new PurchaseOrderList();
		purList.loadPurchaseOrders(name);	
		orderNo = 0;
		
        client = new LoggerClient();
	}


	private void incrementCounter() {
		orderNo++;		
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
			
			this.count = 0;
			
			Map<String, Product> proMap = proList.getProductList();
			
			for (String key : proMap.keySet()) {

				   if(key.equalsIgnoreCase(aPO.getProductType().getProductType()))
			       {
					   Product product = proMap.get(key);
					   
					   if(product.getUnitPrice()<= aPO.getProductType().unitPrice)
					   {
						  int quant =  aPO.getQuantity();

						   while (quant > 0)
						   {
							   if(quant <= 100){
								   produce(aPO.getProductType(),quant);
								   quant = quant - 100;
								 
							   } else
							   {
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
			client.write(e.toString());
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
			this.count++;
			
			if(count <= 1)
			{
			order.setQuantity(quantity);
			}
			else
			{
			order.setQuantity(order.getQuantity()+quantity);
			}
			
			try {
				purList.getPurchaseOrderList().put(Integer.toString(this.orderNo),this.order);
				purList.replenish(this.manuName);
				client.write("Produced product in "+this.manuName+" :"+productType.getProductType()+ "of Quantity "+quantity);
				return true;
			} catch (Exception e) {
				client.write(e.toString());
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
			client.write("Returning ProductInfo of "+this.manuName);
			return proList.getProductInfo(typ);
		} catch (Exception e) {
			
			client.write(e.toString());
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
					  client.write("Updating Payment Status of"+this.manuName+"Order NO"+orderNum);
					  return true;
				  }					  
				  else				  
					  return false;			   
		       }
			   return false;
			}
		}catch (Exception e)
		{
			client.write(e.toString());
			e.printStackTrace();
		}

		return false;
	}

}
