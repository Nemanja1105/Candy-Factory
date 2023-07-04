package services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.unibl.etf.model.Order;

public class MailService {
	private static final String MAIL_CONFIG_PATH=".\\resources\\mailConfig.properties";
	
	public void sendMail(Order order) throws AddressException, MessagingException, IOException {
		  Properties properties=new Properties();
	        properties.load(new FileInputStream(MAIL_CONFIG_PATH));
	        String username=properties.getProperty("username");
	        String password=properties.getProperty("password");
	        Session session=Session.getDefaultInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username,password);
	            }
	        });
	        MimeMessage message=new MimeMessage(session);
	        message.setFrom(new InternetAddress(username,"Fabrika slatkica"));
	        message.setRecipient(Message.RecipientType.TO,new InternetAddress(order.getEmail()));
	        message.setSubject("Informacije o narudzbi");
	        message.setText(order.toString());
	        Transport.send(message);
	}

}
