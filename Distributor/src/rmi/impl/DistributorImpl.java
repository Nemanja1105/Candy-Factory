package rmi.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

import org.unibl.etf.model.Material;

import javafx.collections.ObservableList;
import rmi.DistributorInterface;

public class DistributorImpl implements DistributorInterface {

	private ObservableList<Material> list;

	public DistributorImpl(ObservableList<Material> list) {
		this.list = list;
	}

	@Override
	public List<Material> getAll() {
		synchronized (list) {
			return list.stream().toList();
		}
	}

	@Override
	public boolean buy(int id, int quantity) {
		synchronized (list) {
			var index = list.indexOf(new Material(id));
			if (index != -1) {
				Material el = list.get(index);
				if (el.getAvailableQuantity() >= quantity)
				{
					el.setAvailableQuantity(el.getAvailableQuantity()-quantity);
					return true;
				}
				else
					return false;
			} else
				return false;
		}
	}

}
