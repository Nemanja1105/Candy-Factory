package org.unibl.etf.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.unibl.etf.util.ConfigReader;

public class PromotionService {
	
	private String address;
	private int port;
	
	public PromotionService() {
		ConfigReader configReader=ConfigReader.getInstance();
		this.address=configReader.getMulticastAddress();
		this.port=Integer.parseInt(configReader.getMulticastPort());
	}
	
	public void sendPromotion(String message)throws IOException {
		try(MulticastSocket socket=new MulticastSocket())
		{
			InetAddress address=InetAddress.getByName(this.address);
			socket.joinGroup(address);
			var buffer=message.getBytes();
			DatagramPacket packet=new DatagramPacket(buffer, buffer.length,address,port);
			socket.send(packet);
		} 
	}
}
