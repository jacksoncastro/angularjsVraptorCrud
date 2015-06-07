package br.com.jackson.helper;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public final class QueryHelper {

	private QueryHelper() {
	}


	/**
	 * Dealing case the query has returned null because in
	 * to run the method getSingle may generate a exception
	 *
	 * @author Jackson Castro
	 * @since 2014-06-16
	 */
	public static <T> T getSingleResult(TypedQuery<T> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}