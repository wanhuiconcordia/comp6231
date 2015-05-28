package retailer;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class Retailer {
	//This method starts a RMI registry on the local host, if
	//it does not already exists at the specified port number.
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
			RetailerImplimentation retailerObj = new RetailerImplimentation();
			Naming.rebind("rmi://localhost:1099/retailer", retailerObj);
			System.out.println("Retailer server is ready.");
		}
		catch (Exception e) {
			System.out.println("Exception in HelloServer.main: " + e);
		}
	}

}
