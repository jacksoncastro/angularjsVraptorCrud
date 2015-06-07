package br.com.jackson.service;

import java.util.List;

import javax.inject.Inject;

import br.com.jackson.dao.UserDao;
import br.com.jackson.interfaces.Crud;
import br.com.jackson.model.User;

public class UserService implements Crud<User> {

	@Inject
	private UserDao userDao;

	@Override
	public void save(User user) {
		userDao.insert(user);
	}

	@Override
	public void delete(int id) {
		userDao.remove(id);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public User find(int id) {
		return userDao.findById(id);
	}

	@Override
	public List<User> list() {
		return userDao.findAll();
	}
}