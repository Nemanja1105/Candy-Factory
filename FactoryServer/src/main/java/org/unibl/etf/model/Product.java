package org.unibl.etf.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Product {
	public static int globalId = 1;
	private int id;
	private String name;
	private double price;
	private int availableQuantity;

	public Product() {
		super();
	}

	public Product(int id, String name, double price, int availableQuantity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
	}

	public Product(String name, double price, int availableQuantity) {
		super();
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.id = globalId++;
	}
	
	public Product(Map<String, String> map) {
		super();
		this.id=Integer.parseInt(map.get("id"));
		this.name=map.get("name");
		this.price=Double.parseDouble(map.get("price"));
		this.availableQuantity=Integer.parseInt(map.get("availableQuantity"));
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
		Product other = (Product) obj;
		return id == other.id;
	}
	
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", availableQuantity=" + availableQuantity
				+ "]";
	}

	public Map<String, String> toMap(){
		HashMap<String, String> map=new HashMap<>();
		map.put("id",Integer.toString(this.id));
		map.put("name",this.name);
		map.put("price", Double.toString(this.price));
		map.put("availableQuantity", Integer.toString(this.availableQuantity));
		return map;
	}

}
