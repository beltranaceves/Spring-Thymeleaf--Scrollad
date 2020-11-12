package es.udc.fi.dc.fd.service.like;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.LikeEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface LikeService {
	
	LikeEntity addLike(UserEntity user, AdEntity adLiked);
	
	Iterable<AdEntity> getAdsLikedByUser(UserEntity user);

	void deleteByAdLikedIdAndUserId(final Integer adId, final Integer userId);
}
