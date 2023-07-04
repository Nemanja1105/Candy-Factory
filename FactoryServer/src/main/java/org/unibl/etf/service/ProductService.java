package org.unibl.etf.service;

import java.util.List;
import java.util.logging.Level;

import org.unibl.etf.dao.ProductDAO;
import org.unibl.etf.model.Product;
import org.unibl.etf.util.MyLogger;

public class ProductService {

	private ProductDAO productDAO;

	public ProductService() {
		this.productDAO = new ProductDAO();

	}

	public List<Product> getAll() {
		return this.productDAO.getAll();
	}

	public Product getById(int id) {
		return this.productDAO.getById(id);
	}

	public void insert(Product product) {
		this.productDAO.insert(product);
	}

	public void update(Product product) {
		this.productDAO.update(product);
	}

	public void delete(int id) {
		this.productDAO.delete(id);
	}

	static {
		ProductDAO productDAO = new ProductDAO();
		try {
			Product.globalId = productDAO.getMaxId() + 1;
			if (Product.globalId == 1) {
				var list = new Product[] { new Product("Cokolada", 5, 150), new Product("Puding", 1, 400),
						new Product("Karamele", 2, 200), new Product("Napolitanke", 3, 150),
						new Product("Rafaelo", 4.3, 120), new Product("Suvi kolaci", 6, 60),
						new Product("Sladoled", 2.5, 500) };
				for (var product : list)
					productDAO.insert(product);
			}
		} catch (Exception e) {
			Product.globalId=1;
			e.printStackTrace();
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
		}
	}

}
