package es.udc.fi.dc.fd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.udc.fi.dc.fd.model.persistence.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

	public Iterable<LikeEntity> findByUserId(final Integer id);
		
}
