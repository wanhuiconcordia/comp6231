/**
 * 
 */
package manufacturer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tools.Product;
import tools.PurchaseOrder;

/**
 * @author Ratzzz
 *
 */
public interface ManufacturerInterface extends Remote {
  
  public boolean processPurchaseOrder(PurchaseOrder aPO) throws RemoteException;

  public Product getProductInfo(String aProdName) throws RemoteException;
  
  public boolean receivePayment(String orderNum, float totalPrice) throws RemoteException;
  
}
