package org.unibl.etf.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;

import org.unibl.etf.model.Product;
import org.unibl.etf.util.ConfigReader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ProductDAO {
	
	private static final String GLOBAL_KEY="product:";
	
	private JedisPool jedisPool;
	
	
	
	public ProductDAO() {
		ConfigReader configReader=ConfigReader.getInstance();
		this.jedisPool=new JedisPool(configReader.getRedisConnection());
	}
	
	public List<Product> getAll(){
		ArrayList<Product> result=new ArrayList<>();
		try(Jedis jedis=jedisPool.getResource()){
			var keys=jedis.keys(GLOBAL_KEY+"*");
			for(var key:keys) {
				var map=jedis.hgetAll(key);
				result.add(new Product(map));
			}
		}
		return result;
	}
	
	public Product getById(int id) {
		try(Jedis jedis=jedisPool.getResource()){
			var key=GLOBAL_KEY+id;
			var map=jedis.hgetAll(key);
			if(map!=null)
				return new Product(map);
			else 
				return null;
				
			
		}
	}
	
	public void insert(Product product) {
		try(Jedis jedis=jedisPool.getResource()){
			jedis.hmset(GLOBAL_KEY+product.getId(), product.toMap());
		}
	}
	
	public void update(Product product) {
		try(Jedis jedis=jedisPool.getResource()){
			jedis.hmset(GLOBAL_KEY+product.getId(),product.toMap());
		}
	}
	
	public void delete(int id) {
		try(Jedis jedis=jedisPool.getResource()){
			jedis.del(GLOBAL_KEY+id);
		}
	}
	
	public int getMaxId() {
		try(Jedis jedis=jedisPool.getResource()){
			var keys=jedis.keys(GLOBAL_KEY+"*");
		var max=keys.stream().mapToInt((el)->{
				return Integer.parseInt(el.split(":")[1]);
			}).max();
		return max.isPresent()?max.getAsInt():0;
		}
		
	}
}
