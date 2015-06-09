package retailer;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tools.LoggerClient;


/**
 * @author comp6231.team5
 *
 */
public class Retailer {
	
	/**
	 * This method starts a RMI registry on the local host, if
	 *it does not already exists at the specified port number.
	 * @param RMIPortNum
	 * @throws RemoteException
	 */
	private static void startRegistry(int RMIPortNum) throws RemoteException{
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list( );  
		}
		catch (RemoteException e) {				// No valid registry at that port. 
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
		}
	}
	
	/**
	 * start retailer server, and register a remote RegailerImplimentation object.
	 * @param args
	 */
	public static void main(String args[]) {
		try{     
			startRegistry(1099);
			LoggerClient loggerClient = new LoggerClient();
			RetailerImplimentation retailerObj = new RetailerImplimentation(loggerClient);
			Naming.rebind("rmi://localhost:1099/retailer", retailerObj);
			System.out.println("Retailer server is ready.");
		}
		catch (Exception e) {
			System.out.println("Exception in Retailer.main: " + e);
		}
	}

}
