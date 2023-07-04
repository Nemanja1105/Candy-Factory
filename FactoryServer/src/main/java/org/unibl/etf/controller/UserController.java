package org.unibl.etf.controller;

import java.util.List;
import java.util.logging.Level;

import javax.enterprise.inject.New;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.unibl.etf.exception.NotFoundException;
import org.unibl.etf.exception.UserAlreadyExistsException;
import org.unibl.etf.exception.UserBlockedException;
import org.unibl.etf.exception.UserNotActivatedException;
import org.unibl.etf.model.User;
import org.unibl.etf.model.UserLoginDTO;
import org.unibl.etf.service.UserService;
import org.unibl.etf.util.Message;
import org.unibl.etf.util.MyLogger;

@Path("/users")
public class UserController {
	private static final int NOT_SUCCESS = 500;
	private static final int NOT_FOUND = 404;
	private static final int LOGIN_NOT_SUCCESS = 401;
	private static final int LOGIN_FORBIDDEN = 403;
	private static final int USERNAME_EXISTS = 409;

	private UserService userService;

	public UserController() {
		this.userService = new UserService();
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		try {
			if (this.userService.register(user))
				return Response.ok().build();
			else
				return Response.status(NOT_SUCCESS).build();
		} catch (UserAlreadyExistsException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			return Response.status(USERNAME_EXISTS).entity(new Message(e.getMessage())).build();
		}
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserLoginDTO userLoginDTO) {
		try {
			var user = userService.checkLogin(userLoginDTO);
			return Response.ok().entity(user).build();
		} catch (NotFoundException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			return Response.status(LOGIN_NOT_SUCCESS).entity(new Message(e.getMessage())).build();
		} catch (UserBlockedException | UserNotActivatedException e) {
			MyLogger.logger.log(Level.WARNING,e.getMessage());
			return Response.status(LOGIN_FORBIDDEN).entity(new Message(e.getMessage())).build();
		}
	}

}
