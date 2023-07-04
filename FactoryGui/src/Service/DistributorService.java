package Service;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.unibl.etf.model.Material;
import org.unibl.etf.util.ConfigReader;

import rmi.DistributorInterface;

public class DistributorService implements DistributorInterface{

	private DistributorInterface stub;
	private Registry registry;

	public DistributorService() throws RemoteException {
		ConfigReader reader=ConfigReader.getInstance();
		this.registry = LocateRegistry.getRegistry(Integer.parseInt(reader.getRMIPort()));
		
	}
	
	public void connect(String name) throws AccessException, RemoteException, NotBoundException {
		this.stub=(DistributorInterface)registry.lookup(name);
	}

	public String[] getDistributorsList() throws AccessException, RemoteException {
		return this.registry.list();
	}
	
	public List<Material> getAll() throws RemoteException{
		return this.stub.getAll();
	}

	@Override
	public boolean buy(int id, int quantity) throws RemoteException {
		return this.stub.buy(id, quantity);
	}

}
