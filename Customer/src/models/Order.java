package models;

import java.util.List;

public class Order {
	private List<ProductOrder> orders;
	
	private String clientUsername;
	private String email;
	private boolean status;
	
	public Order(List<ProductOrder> orders,String clientUsername, String email) {
		super();
		this.orders = orders;
		this.email = email;
		this.clientUsername=clientUsername;
		this.status = false;
	}

	public List<ProductOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ProductOrder> orders) {
		this.orders = orders;
	}
	
	public String getClientUsername() {
		return clientUsername;
	}

	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
