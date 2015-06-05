/**
 * 
 */
package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import manufacturer.Constant;
import manufacturer.ManufacturerInterface;
import retailer.RetailerInterface;
import tools.Product;
import tools.PurchaseOrder;
import tools.PurchaseOrderList;

/**
 * @author Ratzzz
 *
 */
public class ManuClient {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
		
		//Registry reg1 = LocateRegistry.getRegistry("localhost", Constant.MANU1_RMI_PORT);
        ManufacturerInterface intobj1 = (ManufacturerInterface) Naming.lookup("rmi://localhost:1099/Manufacturer1");	
		Product pro = new Product("Manufacturer1", "video camera", new Float(1300));
		PurchaseOrder pur = new PurchaseOrder(0,"Rat", pro, 23, 300, 1500, false);
		intobj1.processPurchaseOrder(pur);
		
		
		//Registry reg2 = LocateRegistry.getRegistry("localhost", Constant.MANU2_RMI_PORT);
        ManufacturerInterface intobj2 = (ManufacturerInterface) Naming.lookup("rmi://localhost:1099/Manufacturer2");
        Product pro2 = new Product("Manufacturer2", "TV", new Float(1300));
		PurchaseOrder pur2 = new PurchaseOrder(0,"Fuck2", pro2, 46, 300, 1500, false);
		intobj2.processPurchaseOrder(pur2);

		
		//Registry reg3 = LocateRegistry.getRegistry("localhost", Constant.MANU3_RMI_PORT);
        ManufacturerInterface intobj3 = (ManufacturerInterface) Naming.lookup("rmi://localhost:1099/Manufacturer3");
        Product pro3 = new Product("Manufacturer3", "DVD Player", new Float(1300));
		PurchaseOrder pur3 = new PurchaseOrder(0,"Fuck3", pro3, 90, 300, 1500, false);
		intobj3.processPurchaseOrder(pur3);

	}

}
