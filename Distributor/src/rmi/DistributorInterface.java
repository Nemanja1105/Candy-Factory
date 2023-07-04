package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.unibl.etf.model.Material;

public interface DistributorInterface extends Remote{
	List<Material> getAll()throws RemoteException;
	boolean buy(int id,int quantity)throws RemoteException;

}
