package service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import models.Order;
import util.ConfigReader;

public class OrderService {
	
	public static Connection createConnection() throws IOException, TimeoutException {
		ConfigReader configReader=ConfigReader.getInstance();
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(configReader.getMQHost());
        factory.setUsername(configReader.getMQUsername());
        factory.setPassword(configReader.getMQPassword());
        return factory.newConnection();
    }
	
	
	public void sendOrder(Order order) throws IOException, TimeoutException {
		ConfigReader configReader=ConfigReader.getInstance();
		String value=convertToXML(order);
		try(Connection connection=createConnection()){
			Channel channel=connection.createChannel();
			 channel.queueDeclare(configReader.getMQQueueName(), true, false, false, null);
			 channel.basicPublish("", configReader.getMQQueueName(), null, value.getBytes("UTF-8"));
			 channel.close();
		}
	}
	
	public String convertToXML(Order order) throws JsonProcessingException {
		  XmlMapper xmlMapper = new XmlMapper();
		  String xml = xmlMapper.writeValueAsString(order);
		  return xml;
	}

}
