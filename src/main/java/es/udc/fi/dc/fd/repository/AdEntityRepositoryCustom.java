package es.udc.fi.dc.fd.repository;

import java.util.List;

import es.udc.fi.dc.fd.model.persistence.AdEntity;

public interface AdEntityRepositoryCustom {

	List<AdEntity> find(String city, String text, String interval,Double averageScore, Double minPrice, Double maxPrice);

}
