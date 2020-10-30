package es.udc.fi.dc.fd.service.like;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.LikeEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.LikeRepository;
import es.udc.fi.dc.fd.service.ad.AdEntityService;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

	private final LikeRepository likeRepository;
	
	@Autowired
	AdEntityService adService;

	@Autowired
	public LikeServiceImpl(LikeRepository likeRepository) {
		super();
		this.likeRepository = checkNotNull(likeRepository, "Received a null pointer as repository");
	}

	@Override
	public LikeEntity addLike(UserEntity user, AdEntity adLiked) {
		LikeEntity like = new LikeEntity();
		like.setUser(user);
		like.setAdLiked(adLiked);
		return likeRepository.save(like);
	}

	@Override
	public Iterable<AdEntity> getAdsLikedByUser(UserEntity user) {

		Iterable<LikeEntity> likeIds = likeRepository.findByUserId(user.getId());
		List<AdEntity> ads = new ArrayList<AdEntity>();
		Iterator<LikeEntity> iterator = likeIds.iterator();
		while (iterator.hasNext()) {
			LikeEntity like =  iterator.next();
			if (like.getUser().getId() == user.getId()) {
				AdEntity ad = adService.findById(like.getAdLiked().getId());
				ads.add(ad);
			}
		}

		return ads;
	}

	@Override
	public void delete(LikeEntity like) {
		likeRepository.delete(like);

	}

}
