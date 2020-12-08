package es.udc.fi.dc.fd.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{

	public Iterable<OrderEntity> findByUser(final User user, Sort sort);
	
	public OrderEntity findByAd(final AdEntity ad);
}
