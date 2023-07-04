package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.model.Product;

import util.ConfigReader;

public class ProductService {

	public List<Product> getAll() {
		ConfigReader configReader = ConfigReader.getInstance();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(configReader.getProductsURL());
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		return new ArrayList<>(Arrays.asList(response.readEntity(Product[].class)));
	}
}