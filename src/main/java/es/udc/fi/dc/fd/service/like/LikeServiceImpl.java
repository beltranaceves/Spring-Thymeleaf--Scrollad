package es.udc.fi.dc.fd.service.like;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.LikeEntity;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.LikeRepository;
import es.udc.fi.dc.fd.repository.OrderRepository;
import es.udc.fi.dc.fd.service.ad.AdEntityService;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

	private final LikeRepository likeRepository;
	
	private final OrderRepository orderEntityRepository;

	@Autowired
	AdEntityService adService;

	@Autowired
	public LikeServiceImpl(LikeRepository likeRepository,
			final OrderRepository orderRepository) {
		
		super();
		
		this.likeRepository = checkNotNull(likeRepository, "Received a null pointer as repository");
	
		orderEntityRepository = checkNotNull(orderRepository, "Received a null pointer as repository");
	}

	@Override
	public LikeEntity addLike(UserEntity user, AdEntity adLiked) {
		LikeEntity like = new LikeEntity();
		like.setUser(user);
		like.setAdLiked(adLiked);
		if (likeRepository.findByAdLikedIdAndUserId(adLiked.getId(), user.getId()) == null)
			return likeRepository.save(like);
		else
			return null;
	}

	@Override
	public Iterable<AdEntity> getAdsLikedByUser(UserEntity user) {

		Iterable<LikeEntity> likeIds = likeRepository.findByUserId(user.getId());
		List<AdEntity> ads = new ArrayList<AdEntity>();
		List<AdEntity> adEntitiesList = new ArrayList<AdEntity>();
		Iterator<LikeEntity> iterator = likeIds.iterator();
		while (iterator.hasNext()) {
			LikeEntity like = iterator.next();
			if (like.getUser().getId() == user.getId()) {
				AdEntity ad = adService.findById(like.getAdLiked().getId());
				ads.add(ad);
			}
		}
		ads.forEach((adEntity) -> {

			OrderEntity order = orderEntityRepository.findByAd(adEntity);

			if (order != null) {
				if (!adEntity.getIsOnHold()) {
					if (order.getDate().plusDays(1).compareTo(LocalDateTime.now()) >= 0) {
						adEntity.getImages().forEach((image) -> {
							image.convertAndLoadImageBase64();
						});
						adEntitiesList.add(adEntity);
					}
				}
			} else if (!adEntity.getIsOnHold()) {
				adEntity.getImages().forEach((image) -> {
					image.convertAndLoadImageBase64();
				});
				adEntitiesList.add(adEntity);
			}
		});

		return ads;
	}

	@Override
	public void deleteByAdLikedIdAndUserId(final Integer adId, final Integer userId) {
		LikeEntity like = likeRepository.findByAdLikedIdAndUserId(adId, userId);
		likeRepository.deleteById(like.getId());
	}

}
