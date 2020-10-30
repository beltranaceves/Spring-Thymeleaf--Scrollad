package es.udc.fi.dc.fd.service.like;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.LikeEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface LikeService {
	
	public LikeEntity addLike(UserEntity user, AdEntity adLiked);
	
	public Iterable<AdEntity> getAdsLikedByUser(UserEntity user);

	public void delete(LikeEntity like);

}
