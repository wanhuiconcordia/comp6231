package manufacturer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import tools.Item;
import tools.Product;

/**
 * @author comp6231.team5
 * Defines all the remote call for warehouses
 */
public interface ManufacturerInterface extends Remote{
	/**
	 * Process customer's purchase request
	 * @param item
	 * @return orderNumber
	 * @throws RemoteException
	 */
	public String processPurchaseOrder(Item item)throws RemoteException;
	
	/**
	 * Process customer's get product info request
	 * @param String
	 * @return Product
	 * @throws RemoteException
	 */
	public Product getProductInfo(String aProdName)throws RemoteException;
	
	/**
	 * Process customer's receivePayment request
	 * @param order number
	 * @param total price
	 * @return true/false
	 * @throws RemoteException
	 */
	public boolean receivePayment(String orderNum, float totalPrice)throws RemoteException;
	
	/**
	 * Process customer's get product list request
	 * @return ArrayList<Product>
	 * @throws RemoteException
	 */
	public ArrayList<Product> getProductList()throws RemoteException;
}
