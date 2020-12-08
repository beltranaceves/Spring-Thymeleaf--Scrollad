package es.udc.fi.dc.fd.service.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;
import es.udc.fi.dc.fd.repository.AdEntityRepository;
import es.udc.fi.dc.fd.repository.ImageEntityRepository;
import es.udc.fi.dc.fd.repository.OrderRepository;

@Service
public class AdEntityServiceImpl implements AdEntityService {

	private final AdEntityRepository adEntityRepository;

	private final ImageEntityRepository imageEntityRepository;

	private final OrderRepository orderEntityRepository;

	@Autowired
	public AdEntityServiceImpl(final AdEntityRepository repository, final ImageEntityRepository imageRepository,
			final OrderRepository orderRepository) {
		super();

		adEntityRepository = checkNotNull(repository, "Received a null pointer as repository");

		imageEntityRepository = checkNotNull(imageRepository, "Received a null pointer as repository");

		orderEntityRepository = checkNotNull(orderRepository, "Received a null pointer as repository");
	}

	@Override
	public final Ad add(final AdEntity entity) {
		return adEntityRepository.save(entity);
	}

	@Override
	public final AdEntity findById(final Integer identifier) {
		final AdEntity entity;
		checkNotNull(identifier, "Received a null pointer as identifier");
		Optional<AdEntity> imageEntity = adEntityRepository.findById(identifier);
		if (imageEntity.isPresent()) {
			entity = imageEntity.get();
		} else {
			entity = new AdEntity();
		}

		return entity;
	}

	public void deleteById(final Integer identifier) {
		adEntityRepository.deleteById(identifier);
	}

	public final Iterable<AdEntity> findAds(String city, String keywords, String interval, Double minPrice,
			Double maxPrice) {

		Iterable<AdEntity> adEntities = adEntityRepository.find(city, keywords, interval, minPrice, maxPrice);
		List<AdEntity> adEntitiesList = new ArrayList<AdEntity>();

		adEntities.forEach((adEntity) -> {

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
		return adEntitiesList;
	}

	@Override
	public final List<String> getCities() {
		Iterable<AdEntity> ads = adEntityRepository.findAll();

		List<String> cities = new ArrayList<String>();

		ads.forEach(ad -> {
			String city = ad.getUserA().getCity();
			if (cities.isEmpty() || (!city.matches(cities.get(cities.size() - 1)))) {
				cities.add(city);
			}
		});
		return cities;
	}

	@Override
	public final List<AdEntity> getAllEntities() {

		Iterable<AdEntity> adEntities = adEntityRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
		List<AdEntity> adEntitiesList = new ArrayList<AdEntity>();
		adEntities.forEach((adEntity) -> {

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
		return adEntitiesList;
	}

	@Override
	public final Iterable<AdEntity> getEntitiesByUser(final User user) {
		Iterable<AdEntity> adEntities = adEntityRepository.findByUserA(user, Sort.by(Sort.Direction.DESC, "date"));
		adEntities.forEach((adEntity) -> {
			adEntity.getImages().forEach((image) -> {
				image.convertAndLoadImageBase64();
			});
		});
		return adEntities;
	}

	@Override
	public final List<AdEntity> getEntities(final Pageable page) {
		Iterable<AdEntity> adEntities = adEntityRepository.findAll(page);
		List<AdEntity> adEntitiesList = new ArrayList<AdEntity>();

		adEntities.forEach((adEntity) -> {

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
		return adEntitiesList;
	}

	@Override
	public final void updateIsOnHoldById(final Integer adEntityId) {
		Optional<AdEntity> adEntity = adEntityRepository.findById(adEntityId);
		if (adEntity.isPresent()) {
			adEntity.get().setIsOnHold(!adEntity.get().getIsOnHold());
			adEntityRepository.save(adEntity.get());
		}
	}

	@Override
	public final void updateIsSoldById(final Integer adEntityId) {
		Optional<AdEntity> adEntity = adEntityRepository.findById(adEntityId);
		if (adEntity.isPresent()) {
			adEntity.get().setIsSold(!adEntity.get().getIsSold());
			adEntityRepository.save(adEntity.get());
		}
	}

	@Override
	public final void remove(final AdEntity entity) {
		adEntityRepository.delete(entity);
	}

	@Override
	public boolean checkForm(final AdForm adForm) {
		checkNotNull(adForm.getTitle(), "Received a null pointer as a title");
		checkNotNull(adForm.getDescription(), "Received a null pointer as description");
		return false;
	}
}
