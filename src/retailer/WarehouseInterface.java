package retailer;
import java.rmi.RemoteException;
public interface WarehouseInterface extends java.rmi.Remote{
	//public ItemShippingStatus shipGoods(ArrayList<Item> itemList, Customer customer);
	public String notifyMe(String message) throws RemoteException;
}
