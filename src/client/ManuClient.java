/**
 * 
 */
package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import manufacturer.Constant;
import manufacturer.ManufacturerInterface;
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
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {
		
		Registry reg1 = LocateRegistry.getRegistry("localhost", Constant.MANU1_RMI_PORT);
        ManufacturerInterface intobj1 = (ManufacturerInterface) reg1.lookup(Constant.MANU1_RMI_ID);
		//System.out.println(intobj1.isLoginValid("rmi"));
	//	System.out.println(intobj1.getProductInfo("video camera"));
		System.out.println(intobj1.receivePayment("1", new Float(200)));
		
		Product pro = new Product("Manufacturer1", "Hello", new Float(300));
		PurchaseOrder pur = new PurchaseOrder(0,"Rat", pro, 500, 300, 1500, false);
		System.out.println(intobj1.processPurchaseOrder(pur));
		
		
//		Registry reg2 = LocateRegistry.getRegistry("localhost", Constant.MANU2_RMI_PORT);
//        ManufacturerInterface intobj2 = (ManufacturerInterface) reg2.lookup(Constant.MANU2_RMI_ID);
//       // System.out.println(intobj2.getProductInfo("video camera"));
//        System.out.println(intobj2.receivePayment("2", new Float(400)));
//		
//		Registry reg3 = LocateRegistry.getRegistry("localhost", Constant.MANU3_RMI_PORT);
//        ManufacturerInterface intobj3 = (ManufacturerInterface) reg3.lookup(Constant.MANU3_RMI_ID);
//       // System.out.println(intobj3.getProductInfo("Hello"));
//        System.out.println(intobj3.receivePayment("3", new Float(200)));
	}

}
