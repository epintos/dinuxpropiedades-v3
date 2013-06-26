package ar.edu.itba.it.paw.domain;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ar.edu.itba.it.paw.domain.publications.OrderEnum;

public class AbstractHibernateRepo {
	private final SessionFactory sessionFactory;

	public AbstractHibernateRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type, Serializable id) {
		return (T) getSession().get(type, id);
	}

	
	public void update(String hql, Object... params) {
		Session session = getSession();

		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
	}

	protected org.hibernate.classic.Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Serializable save(Object o) {
		return getSession().save(o);
	}

	public <T> List<T> getAll(Class<T> clazz) {

		Criteria crit = getCriteria(clazz);

		return listResults(crit);

	}

	public <T> Criteria getCriteria(Class<T> clazz) {
		Session sess = getSession();
		return sess.createCriteria(clazz);

	}

	public void addEqualRestriction(Criteria crit, String propertyName, Object value) {
		crit.add(Restrictions.eq(propertyName, value));
	}

	public void addNotEqualRestriction(Criteria crit, String propertyName, Object value) {
		crit.add(Restrictions.not(Restrictions.eq(propertyName, value)));
	}

	public void addOrder(Criteria crit, OrderEnum order, String propertyName) {
		if (order == OrderEnum.ASC)
			crit.addOrder(Order.asc(propertyName));
		else
			crit.addOrder(Order.desc(propertyName));
	}

	public void addBetweenRestriction(Criteria crit, String propertyName,
			Integer valueFrom, Integer valueTo) {
		if (valueFrom == null && valueTo != null) {
			crit.add(Restrictions.le(propertyName, valueTo));
			return;
		}
		if (valueFrom != null && valueTo == null) {
			crit.add(Restrictions.ge(propertyName, valueFrom));
		}

		if (valueFrom != null && valueTo != null) {

			crit.add(Restrictions.between(propertyName, valueFrom, valueTo));
		}

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> listResults(Criteria crit) {
		return crit.list();
	}

}