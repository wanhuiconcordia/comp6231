package manufacturer;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface ManufacturerInterface extends Remote{
	public boolean processPurchaseOrder(String msg) throws RemoteException;//TODO TMP SIGNATURE TO BE MODIFIED
}
