package br.com.jackson.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import br.com.caelum.vraptor.util.StringUtils;
import br.com.jackson.helper.QueryHelper;

public class GenericDao<T extends Serializable> {

	@Inject
	private EntityManager entity;

	/**
	 * Busca um objeto genérico pela chave primária
	 * 
	 * @param objeto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T find(T objeto) {
		PersistenceUnitUtil util = entity.getEntityManagerFactory().getPersistenceUnitUtil();
		Object idPrimaryKey = util.getIdentifier(objeto);

		if (idPrimaryKey != null) {
			return (T) entity.find(objeto.getClass(), idPrimaryKey);
		}
		return null;
	}


	/**
	 * Busca um objeto genérico pela chave campo
	 * 
	 * @param campo
	 * @param objeto
	 * @return
	 */
	public T findBy(String campo, T objeto) {

		String campoGet = "get" + StringUtils.capitalize(campo);
		try {
			Object valor = objeto.getClass().getMethod(campoGet).invoke(objeto);
			String hql = "select g FROM " + objeto.getClass().getName() + " g where g." + campo + " = :campo";
			Query query = entity.createQuery(hql);
			query.setParameter("campo", valor);

			return getResultAsSingle(query);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Busca todos os elementos do tipo do objeto
	 * 
	 * @param objeto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		String hql = "select g FROM " + getTypeClass().getSimpleName() + " g";
		Query query = entity.createQuery(hql);
		return query.getResultList();
	}


	/**
	 * Busca todos os elementos do tipo do objeto ordernados 
	 * 
	 * @param objeto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllOrdenados() {
		String hql = "select g FROM " + getTypeClass().getName() + " g order by g.id asc";
		Query query = entity.createQuery(hql);
		return query.getResultList();
	}

	/**
	 * Insere um elemento ao banco
	 * 
	 * @param objeto
	 * @return
	 */
	public T insert(T objeto) {
		entity.persist(objeto);
		return objeto;
	}


	/**
	 * Faz o update de um elemento no banco
	 * 
	 * @param objeto
	 * @return
	 */
	public T update(T objeto) {
		entity.merge(objeto);
		entity.flush();
		return objeto;
	}

	/**
	 * Exclui um elemento do banco
	 * 
	 * @param objeto
	 * @return
	 */
	public T remove(T objeto) {
		entity.remove(objeto);
		entity.flush();
		return objeto;
	}


	/**
	 * Exclui um elemento pelo id
	 */
	public void remove(int id) {
		Object object = entity.getReference(getTypeClass(), id);
		entity.remove(object);
	}


	/**
	 * Busca um objeto por seu Id
	 */
	@SuppressWarnings("unchecked")
	public T findById(int id) {
		String hql = "SELECT g FROM " + getTypeClass().getSimpleName() + " g where g.id = :id";
		TypedQuery<?> query = entity.createQuery(hql, getTypeClass());
		query.setParameter("id", id);
		return (T) QueryHelper.getSingleResult(query);
	}


	/**
	 * Execute a query and return a element
	 *
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getResultAsSingle(Query query) {
		return (T) query.getSingleResult();
	}


	private Class<?> getTypeClass() {
		ParameterizedType parameterizedType = ((ParameterizedType) this.getClass().getGenericSuperclass());
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }


	public Session getSession() {
		return entity.unwrap(Session.class);
	}


	/**
	 * Return instance of EntityManager
	 *
	 * @author Jackson Castro
	 * @since 2015-05-03
	 */
	public EntityManager getEntity() {
		return entity;
	}
}