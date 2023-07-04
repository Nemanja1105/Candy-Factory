package org.unibl.etf.model;

import java.io.Serializable;
import java.util.Objects;

public class Material implements Serializable {
	public static int globalId = 1;
	private int id;
	private String name;
	private int availableQuantity;
	
	public Material() {
		super();
		this.id=globalId++;
	}
	
	public Material(int id) {
		this.id=id;
	}

	public Material(String name, int availableQuantity) {
		super();
		this.name = name;
		this.availableQuantity = availableQuantity;
		this.id=globalId++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		return id == other.id;
	}
	
	

}
