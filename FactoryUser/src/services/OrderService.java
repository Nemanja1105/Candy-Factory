package services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.unibl.etf.model.Order;
import org.unibl.etf.util.MyLogger;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import exceptions.InvalidXMLException;
import utils.ConfigReader;

public class OrderService {
	public static Connection createConnection() throws IOException, TimeoutException {
		ConfigReader configReader = ConfigReader.getInstance();
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(configReader.getMqHost());
		factory.setUsername(configReader.getMqUsername());
		factory.setPassword(configReader.getMqPassword());
		return factory.newConnection();
	}

	public Order popOrder() throws IOException, TimeoutException {
		ConfigReader configReader = ConfigReader.getInstance();
		Order order;
		try (Connection connection = createConnection()) {
			Channel channel = connection.createChannel();
			channel.queueDeclare(configReader.getMqQueueName(), true, false, false, null);
			GetResponse response = channel.basicGet(configReader.getMqQueueName(), false);
			if (response != null) {
				byte[] body = response.getBody();
				String message = new String(body, "UTF-8");
				long deliveryTag = response.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, false);
				order = xmlToOrder(message);
			} else
				order = null;
			channel.close();
		}
		return order;
	}

	private boolean schemaValidation(String in) {
		try {
			ConfigReader reader = ConfigReader.getInstance();
			var schemaSource = new StreamSource(reader.getSchemaPath());
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(schemaSource);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(in)));
			return true;
		} catch (SAXException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private Order xmlToOrder(String in) throws JsonMappingException, JsonProcessingException {
		if (!schemaValidation(in))
			throw new InvalidXMLException();
		System.out.println("aaaa");
		XmlMapper xmlMapper = new XmlMapper();
		Order resultOrder = xmlMapper.readValue(in, Order.class);
		return resultOrder;
	}
}
