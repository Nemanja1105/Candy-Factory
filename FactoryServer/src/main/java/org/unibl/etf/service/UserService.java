package org.unibl.etf.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.enterprise.inject.New;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;
import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.exception.NotFoundException;
import org.unibl.etf.exception.UserAlreadyExistsException;
import org.unibl.etf.exception.UserBlockedException;
import org.unibl.etf.exception.UserNotActivatedException;
import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;
import org.unibl.etf.util.MyLogger;

public class UserService {
	private List<User> users;
	private UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
		this.users = userDAO.getAll();
	}

	public List<User> getAll() {
		return this.userDAO.getAll();
	}
	
	public User getByUsername(String username) {
		this.users=this.getAll();
		var index=this.users.indexOf(new User(username));
		if(index==-1)
			throw new NotFoundException();
		return this.users.get(index);
	}

	public boolean blockUser(String username) {
		this.users=this.getAll();
		int index = this.users.indexOf(new User(username));
		if (index == -1)
			throw new NotFoundException();
		var user = this.users.get(index);
		if (!user.isBlocked()) {
			try {
				user.setBlocked(true);
				this.userDAO.insertAll(users);
			} catch (IOException e) {
				user.setBlocked(false);
				e.printStackTrace();
				MyLogger.logger.log(Level.SEVERE,e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public boolean unblockUser(String username) {
		this.users=this.getAll();
		int index = this.users.indexOf(new User(username));
		if (index == -1)
			throw new NotFoundException();
		var user = this.users.get(index);
		if (user.isBlocked()) {
			try {
				user.setBlocked(false);
				this.userDAO.insertAll(users);
			} catch (IOException e) {
				user.setBlocked(true);
				e.printStackTrace();
				MyLogger.logger.log(Level.SEVERE,e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public boolean activateUser(String username) {
		this.users=this.getAll();
		int index = this.users.indexOf(new User(username));
		if (index == -1)
			throw new NotFoundException();
		var user = this.users.get(index);
		if(!user.isActivated())
		{
			try {
				user.setActivated(true);
				this.userDAO.insertAll(users);
			} catch (IOException e) {
				user.setActivated(false);
				e.printStackTrace();
				MyLogger.logger.log(Level.SEVERE,e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public boolean deleteUser(String username) {
		this.users=this.getAll();
		if(!this.users.remove(new User(username)))
			throw new NotFoundException();
		try {
			this.userDAO.insertAll(users);
		}
		catch(IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean register(User user) {
		this.users=this.getAll();
		try {
			if(this.users.contains(user))
				throw new UserAlreadyExistsException();
			this.users.add(user);
			this.userDAO.insertAll(this.users);
		} catch (IOException e) {
			MyLogger.logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public User checkLogin(UserLoginDTO userLogin) {
		this.users=this.getAll();
		var user = this.users.stream().filter(
				u -> u.getUsername().equals(userLogin.getUsername()) && u.getPassword().equals(userLogin.getPassword()))
				.findFirst().orElseThrow(NotFoundException::new);
		if (!user.isActivated())
			throw new UserNotActivatedException();
		if (user.isBlocked())
			throw new UserBlockedException();
		return user;
	}
}
