package ar.edu.itba.it.paw.domain.publicity;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.AbstractHibernateRepo;

@Repository
public class HibernatePublicityRepo extends AbstractHibernateRepo implements
		PublicityRepo {

	@Autowired
	public HibernatePublicityRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Publicity getRandomPublicity() {
		int total = getTotalFrequence();
		Publicity publicity = null;
		int times = 0;
		while (publicity == null && times < 5) {
			int random = randomInt(0, total);
			publicity = getPublicity(random);
			times++;
		}
		return publicity;

	}

	/**
	 * Returns a random publicity with "frequence" from all the results.
	 * @param frequence
	 * @return
	 */
	private Publicity getPublicity(Integer frequence) {
		Criteria crit = getCriteria(Publicity.class);
		addEqualRestriction(crit, "frequence", frequence);
		List<Publicity> publicities = listResults(crit);
		int size = publicities.size();
		if (size != 0) {
			return publicities.get(randomInt(0, size - 1));
		}
		return null;
	}

	/**
	 * Returns the addition of all the frequences.
	 * @return total frequence
	 */
	private Integer getTotalFrequence() {
		Criteria crit = getCriteria(Publicity.class);
		List<Publicity> publicities = listResults(crit);
		Integer result = 0;
		for (Publicity publicity : publicities) {
			result += publicity.getFrequence();
		}
		return result;
	}

	/**
	 * Returns a random int between min and max.
	 * @param min
	 * @param max
	 * @return
	 */
	private int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
}
