package models;

import java.util.Objects;

import org.unibl.etf.model.Product;

public class ProductOrder {
	private int productId;
	private String productName;
	private double productPrice;
	private int quantity;
	
	public ProductOrder() {
		super();
	}

	public ProductOrder(Product product, int quantity) {
		super();
		this.productId=product.getId();
		this.productName=product.getName();
		this.productPrice=product.getPrice();
		this.quantity = quantity;
	}

	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductOrder other = (ProductOrder) obj;
		return Objects.equals(productId, other.productId);
	}
	
	
}
