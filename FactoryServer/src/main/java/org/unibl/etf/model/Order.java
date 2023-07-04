package org.unibl.etf.model;

import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class Order {
	private List<ProductOrder> orders;
	private String clientUsername;
	private String email;
	private boolean status;
	
	public Order() {
		super();
	}
	
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

	@Override
	public String toString() {
		 StringBuilder builder = new StringBuilder();
		    builder.append("Korisnicko ime narucioca: ").append(this.clientUsername).append("\n");
		    builder.append("Email narucioca: ").append(this.email).append("\n");
		    builder.append("Status: ").append(this.status ? "Odobrena" : "Odbijena").append("\n");
		    builder.append("Stavke narudzbe:\n");
		    for (var el : this.orders) {
		        builder.append(el.toString()).append("\n");
		    }
		    return builder.toString();
		
	}
	
	
}
