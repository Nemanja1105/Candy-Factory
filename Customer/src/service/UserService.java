package service;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;
import org.unibl.etf.util.Message;

import exceptions.InvalidLoginException;
import util.ConfigReader;

public class UserService {

	public User checkLogin(UserLoginDTO userLoginDTO) {
		ConfigReader configReader=ConfigReader.getInstance();
		Client client=ClientBuilder.newClient();
		WebTarget target=client.target(configReader.getLoginURL());
		Response response=target.request(MediaType.APPLICATION_JSON).post(Entity.entity(userLoginDTO, MediaType.APPLICATION_JSON));
		if(response.getStatusInfo()!=Status.OK)
		{
			Message message=response.readEntity(Message.class);
			throw new InvalidLoginException(message.getMessage());
		}
		return response.readEntity(User.class);
	}
	
	public boolean register(User user) {
		ConfigReader configReader=ConfigReader.getInstance();
		Client client=ClientBuilder.newClient();
		WebTarget target=client.target(configReader.getRegisterURL());
		Response response=target.request(MediaType.APPLICATION_JSON).post(Entity.entity(user, MediaType.APPLICATION_JSON));
		if(response.getStatusInfo()==Status.OK)
			return true;
		else if(response.getStatusInfo()==Status.INTERNAL_SERVER_ERROR)
			return false;
		else
		{
			Message message=response.readEntity(Message.class);
			throw new InvalidLoginException(message.getMessage());
		}
	}
}
