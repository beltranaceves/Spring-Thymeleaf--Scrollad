package es.udc.fi.dc.fd.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.udc.fi.dc.fd.model.persistence.AdEntity;

public class AdEntityRepositoryImpl implements AdEntityRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	private String[] getTokens(String keywords) {

		if (keywords == null || keywords.length() == 0) {
			return new String[0];
		} else {
			return keywords.split("\\s");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdEntity> find(String city, String keywords, String interval,Double averageScore, Double minPrice, Double maxPrice) {

		String[] tokens = getTokens(keywords);
		String queryString = "SELECT p FROM Ad p";

		if (city != null) {
			if (city.matches("")) {
				city = null;
			}
		}

		if (interval != null) {
			if (interval.matches("")) {
				interval = null;
			}
		}

		if (city != null || tokens.length > 0 || interval != null || averageScore !=null || minPrice != null || maxPrice != null) {
			queryString += " WHERE ";
		}

		if (city != null) {
			queryString += "p.userA.city = :city";
		}

		if (interval != null) {

			if (city != null) {
				queryString += " AND ";
			}

			if (interval.matches("day")) {
				queryString += "EXTRACT(DAY FROM p.date) = EXTRACT(DAY FROM CURRENT_TIMESTAMP)";

			} else if (interval.matches("week")) {
				queryString += "EXTRACT(WEEK FROM p.date) = EXTRACT(WEEK FROM CURRENT_TIMESTAMP)";

			} else if (interval.matches("month")) {
				queryString += "EXTRACT(MONTH FROM p.date) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP)";

			} else {
				queryString += "EXTRACT(YEAR FROM p.date) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP)";
			}
		}

		if (averageScore != null) {
			
			if (city != null || interval != null) {
				queryString += " AND ";
			}
			
			if (averageScore != null) {
				queryString += " p.userA.averageScore >= :averageScore";
			}			
		}
		
		if (minPrice != null || maxPrice != null) {

			if (city != null || interval != null || averageScore != null) {
				queryString += " AND ";
			}

			if (minPrice != null) {
				queryString += "p.price >= :minPrice";
			}
			if (maxPrice != null) {
				if (minPrice != null) {
					queryString += " AND ";
				}
				queryString += " p.price <= :maxPrice";
			}
		}

		if (tokens.length != 0) {

			if (city != null || interval != null || averageScore != null || minPrice != null || maxPrice != null) {
				queryString += " AND ";
			}

			for (int i = 0; i < tokens.length - 1; i++) {
				queryString += "LOWER(p.title) LIKE LOWER(:token" + i + ") AND ";
			}

			queryString += "LOWER(p.title) LIKE LOWER(:token" + (tokens.length - 1) + ")";

		}

		queryString += " ORDER BY p.date";

		Query query = entityManager.createQuery(queryString);

		if (city != null) {
			query.setParameter("city", city);
		}

		if (averageScore != null) {
			query.setParameter("averageScore", averageScore);
		}
		
		if (minPrice != null) {
			query.setParameter("minPrice", minPrice);
		}

		if (maxPrice != null) {
			query.setParameter("maxPrice", maxPrice);
		}

		if (tokens.length != 0) {
			for (int i = 0; i < tokens.length; i++) {
				query.setParameter("token" + i, '%' + tokens[i] + '%');
			}

		}

		List<AdEntity> ads = query.getResultList();

		return ads;

	}

}
