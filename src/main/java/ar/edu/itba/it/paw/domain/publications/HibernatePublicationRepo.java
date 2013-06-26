package ar.edu.itba.it.paw.domain.publications;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.AbstractHibernateRepo;
import ar.edu.itba.it.paw.domain.users.User;

@Repository
public class HibernatePublicationRepo extends AbstractHibernateRepo implements
		PublicationRepo {
	@Autowired
	public HibernatePublicationRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Publication get(int id) {
		return get(Publication.class, id);
	}

	public List<Publication> getAll(User user, boolean filterFinished) {
		Criteria crit = getCriteria(Publication.class);
		getAll(user, crit, filterFinished);
		addOrder(crit,OrderEnum.ASC,"price");
		return listResults(crit);
	}
	
	public Integer getAllSize(User user, boolean filterFinished) {
		Criteria crit = getCriteria(Publication.class).setProjection(
				Projections.rowCount());
		getAll(user, crit, filterFinished);
		return (Integer) (crit.list().get(0));
		
	}
	
	private void getAll(User user, Criteria crit, boolean filterFinished) {
		addEqualRestriction(crit, "user", user);
		if (filterFinished) {
			addNotEqualRestriction(crit, "status", Status.SOLD);
			addNotEqualRestriction(crit, "status", Status.CANCELED);
		}
	}

	public void add(Publication publication) {
		save(publication);

	}

	public List<Publication> getAll() {
		return getAll(Publication.class);
	}

	public List<Publication> getAll(Search search) {

		Criteria crit = getCriteria(Publication.class);
		getAll(search, crit);

		addOrder(crit, search.getOrder(), "price");
		crit.setMaxResults(search.getResultsPerPage());
		crit.setFirstResult(search.getFrom());

		return listResults(crit);
	}

	public Integer getAllSize(Search search) {

		Criteria crit = getCriteria(Publication.class).setProjection(
				Projections.rowCount());
		getAll(search, crit);
		return (Integer) (crit.list().get(0));
	}

	private void getAll(Search search, Criteria crit) {
		if (search.getOperationType() != null) {
			addEqualRestriction(crit, "operationType", search
					.getOperationType());
		}
		if (search.getPropertyType() != null) {
			addEqualRestriction(crit, "propertyType", search.getPropertyType());
		}
		if(search.getCurrency() != null) {
			addEqualRestriction(crit, "currency", search.getCurrency());
		}
		addBetweenRestriction(crit, "price", search.getPriceFrom(), search
				.getPriceTo());
		addNotEqualRestriction(crit, "status", Status.CANCELED);
		addNotEqualRestriction(crit, "status", Status.SOLD);
	}

}
