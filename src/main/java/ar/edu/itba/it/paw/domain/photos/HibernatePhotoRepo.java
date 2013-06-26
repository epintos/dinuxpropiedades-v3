package ar.edu.itba.it.paw.domain.photos;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.AbstractHibernateRepo;

@Repository
public class HibernatePhotoRepo extends AbstractHibernateRepo implements PhotoRepo {

	@Autowired
	public HibernatePhotoRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	

	public Photo getPhoto(int id) {
		Criteria crit = getSession().createCriteria(Photo.class);
		crit.add(Restrictions.eq("id", id));
		return (Photo)crit.list().get(0);
	}

}
