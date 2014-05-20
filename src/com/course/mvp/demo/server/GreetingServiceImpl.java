package com.course.mvp.demo.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.course.mvp.demo.client.GreetingService;
import com.course.mvp.demo.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	static {
		ObjectifyService.register(User.class);
	}

	public String greetServer(String input) throws IllegalArgumentException {
		return "";
	}

	@Override
	public String checkAccount(String username, String password) {
		// Objectify ofy = ObjectifyService.begin();
		User user = ofy().load().type(User.class).id(username).get();
		if (user == null) {
			user = new User("admin", "admin", 0);
			ofy().save().entity(user).now();
			return "Create admin ! Hello !";
		} else if (user.getPassword().equalsIgnoreCase(password)) {
			return "Hello " + user.getUserName() + ". You are logged in";
		} else {
			return "User name or Password is not valid !" + user.getUserName()
					+ "-" + user.getPassword() + user.getLevelCompleted();
		}
	}

	@Override
	public int setLevelCompleted(int levelCompleted) {
		User user = ofy().load().type(User.class).id("admin").get();
		if (user == null) {
			user = new User("admin", "admin", levelCompleted);
			ofy().save().entity(user).now();
		} else {
			user.setLevelCompleted(levelCompleted);
			ofy().save().entity(user).now();

		}
		return levelCompleted;
	}

	@Override
	public int getLevelCompleted() {
		User user = ofy().load().type(User.class).id("admin").get();
		if (user == null) {
			user = new User("admin", "admin", 0);
			ofy().save().entity(user).now();
			return 0;
		} else {
			return user.getLevelCompleted();
		}

	}
}
