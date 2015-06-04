package manufacturer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Ratzzz
 *
 */
public class Manufacturer {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		try
		{
			ManufacturerImpl impl1 = new ManufacturerImpl("Manufacturer1");
			Registry reg = LocateRegistry.createRegistry(Constant.MANU1_RMI_PORT);
			reg.bind(Constant.MANU1_RMI_ID, impl1);
			System.out.println("Server1 Started !!");
			
//			ManufacturerImpl impl2 = new ManufacturerImpl("Manufacturer2");
//			Registry reg2 = LocateRegistry.createRegistry(Constant.MANU2_RMI_PORT);
//			reg2.bind(Constant.MANU2_RMI_ID, impl2);
//			System.out.println("Server2 Started !!");
//			
//			ManufacturerImpl impl3 = new ManufacturerImpl("Manufacturer3");
//			Registry reg3 = LocateRegistry.createRegistry(Constant.MANU3_RMI_PORT);
//			reg3.bind(Constant.MANU3_RMI_ID, impl3);
//			System.out.println("Server3 Started !!");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
