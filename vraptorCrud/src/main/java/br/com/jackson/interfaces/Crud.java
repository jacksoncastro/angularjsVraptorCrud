package br.com.jackson.interfaces;

import java.util.List;

public interface Crud<T> {

	public void save(T t);

	public void delete(int id);

	public void update(T t);

	public T find(int id);

	public List<T> list();
}