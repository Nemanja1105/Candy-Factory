package org.unibl.etf.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.enterprise.inject.New;

import org.unibl.etf.dao.FactoryUserDAO;
import org.unibl.etf.model.FactoryUser;
import org.unibl.etf.util.ConfigReader;

public class FactoryUserService {
	
	private FactoryUserDAO factoryUserDAO;
	
	public FactoryUserService() {
		this.factoryUserDAO=new FactoryUserDAO();
	}
	
	public boolean checkLogin(String name) {
		return this.factoryUserDAO.getAll().contains(new FactoryUser(name));
	}
	
	public void saveOrder(String order)throws IOException {
		ConfigReader configReader=ConfigReader.getInstance();
		File path=new File(configReader.getOrdersPath()+File.separator+(new Date().getTime())+".txt");
		try(PrintWriter writer=new PrintWriter(new BufferedWriter(new FileWriter(path)))){
			writer.println(order.replace("$", "\n"));
		}
	}
	
	

}
