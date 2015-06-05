package manufacturer;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import retailer.RetailerImplimentation;

/**
 * @author Ratzzz
 *
 */
public class Manufacturer {
	
	private static void startRegistry(int RMIPortNum) throws RemoteException{
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list( );  
		}
		catch (RemoteException e) {				// No valid registry at that port. 
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
		}
	}
	
	public static void main(String args[]) {
		try{     
			startRegistry(1099);
			
				ManufacturerImpl impl1 = new ManufacturerImpl("Manufacturer1");
				Naming.rebind("rmi://localhost:1099/Manufacturer1", impl1);
				System.out.println("Manufacturer 1 Server Started !!");
				
				ManufacturerImpl impl2 = new ManufacturerImpl("Manufacturer2");
				Naming.rebind("rmi://localhost:1099/Manufacturer2", impl2);
				System.out.println("Manufacturer 2 Server Started !!");
				
				ManufacturerImpl impl3 = new ManufacturerImpl("Manufacturer3");
				Naming.rebind("rmi://localhost:1099/Manufacturer3", impl3);
				System.out.println("Manufacturer 3 Server Started !!");
		}
		catch (Exception e) {
			System.out.println("Exception in Retailer.main: " + e);
		}
	}

}
