package br.com.jackson.controller;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.jackson.interfaces.Crud;
import br.com.jackson.model.User;
import br.com.jackson.service.UserService;


@Controller
public class UserController implements Crud<User> {

	@Inject
	private UserService userService;

	@Inject
	private Result result;

	@Post("/user")
	@Consumes("application/json")
	public void save(User user) {
		userService.save(user);
		result.use(Results.json()).withoutRoot().from(user).serialize();
	}

	@Delete("/user/{id}")
	public void delete(int id) {
		userService.delete(id);
		result.nothing();
	}

	@Put("/user")
	@Consumes("application/json")
	public void update(User user) {
		userService.update(user);
		result.use(Results.json()).withoutRoot().from(user).serialize();
	}

	@Get("/user/{id}")
	public User find(int id) {
		User user = userService.find(id);
		result.use(Results.json()).withoutRoot().from(user).serialize();
		return user;
	}

	@Get("/user")
	public List<User> list() {
		List<User> users = userService.list();
		result.use(Results.json()).withoutRoot().from(users).serialize();
		return users;
	}
}