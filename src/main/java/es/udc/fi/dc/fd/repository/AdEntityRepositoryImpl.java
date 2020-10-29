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
	public List<AdEntity> find(String city, String keywords) {

		String[] tokens = getTokens(keywords);
		String queryString = "SELECT p FROM Ad p";

		if (city != null) {
			if (city.matches("")) {
				city = null;
			}
		}

		if (city != null || tokens.length > 0) {
			queryString += " WHERE ";
		}

		if (city != null) {
			queryString += "p.userA.city = :city";
		}

		if (tokens.length != 0) {

			if (city != null) {
				queryString += " AND ";
			}

			for (int i = 0; i < tokens.length - 1; i++) {
				queryString += "LOWER(p.title) LIKE LOWER(:token" + i + ") AND ";
			}

			queryString += "LOWER(p.title) LIKE LOWER(:token" + (tokens.length - 1) + ")";

		}

		queryString += " ORDER BY p.title";

		Query query = entityManager.createQuery(queryString);

		if (city != null) {
			query.setParameter("city", city);
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
