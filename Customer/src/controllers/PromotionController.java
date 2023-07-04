package controllers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import util.ConfigReader;
import util.MyLogger;

public class PromotionController extends Thread{
	
	private String address;
	private int port;
	private TextArea textArea;
	
	public PromotionController(TextArea textArea) {
		ConfigReader reader=ConfigReader.getInstance();
		this.address=reader.getMulticastIp();
		this.port=Integer.parseInt(reader.getMulticastServerPort());
		this.setDaemon(true);
		this.textArea=textArea;
	}
	
	@Override
	public void run() {
		try(MulticastSocket socket=new MulticastSocket(port)){
			var inetAddress=InetAddress.getByName(address);
			socket.joinGroup(inetAddress);
			while(true) {
				byte[] buffer=new byte[1024];
				 DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
				 socket.receive(packet);
				 String promotion = new String(packet.getData(), 0, packet.getLength());
				 Platform.runLater(()->{
					 textArea.setText(promotion);
				 });
			}
		} 
		catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
		
	}
	

}
