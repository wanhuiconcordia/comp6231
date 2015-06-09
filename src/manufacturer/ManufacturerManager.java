package manufacturer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import tools.LoggerClient;

/**
 * @author comp6231.team5
 *
 */
public class ManufacturerManager {
	private LoggerClient loggerClient;
	private ArrayList<ManufacturerImplementation> manufacturerList;
	public ManufacturerManager() throws RemoteException, MalformedURLException, NotBoundException{
		loggerClient = new LoggerClient();
		manufacturerList = new ArrayList<ManufacturerImplementation>();
		manufacturerList.add(new ManufacturerImplementation("manufacturer1", loggerClient));
		manufacturerList.add(new ManufacturerImplementation("manufacturer2", loggerClient));
		manufacturerList.add(new ManufacturerImplementation("manufacturer3", loggerClient));		
	}
	
	/**
	 * @param RMIPortNum
	 * @throws RemoteException
	 */
	private static void startRegistry(int RMIPortNum) throws RemoteException{
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list();  
		}
		catch (RemoteException e) {				// No valid registry at that port. 
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
		}
	}
	
	public static void main(String args[]) {
		try{     
			startRegistry(1099);
			ManufacturerManager manufacturerManager = new ManufacturerManager(); for(ManufacturerImplementation manufacturer: manufacturerManager.manufacturerList){
				System.out.println("tries to rebind rmi://localhost:1099/" + manufacturer.getName());
				Naming.rebind("rmi://localhost:1099/" + manufacturer.getName(), manufacturer);
				System.out.println("Rebound rmi://localhost:1099/" + manufacturer.getName());
				
			}
			System.out.println("Manufacturer server is ready.");
		}
		catch (Exception e) {
			System.out.println("Exception in ManufacturerManager.main: " + e);
		}
	}

}
