package es.udc.fi.dc.fd.service.image;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import es.udc.fi.dc.fd.model.Image;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.repository.ImageEntityRepository;

public class ImageEntityServiceImpl implements ImageEntityService {

	private final ImageEntityRepository imageEntityRepository;

	@Autowired
	public ImageEntityServiceImpl(final ImageEntityRepository repository) {
		super();

		imageEntityRepository = checkNotNull(repository, "Received a null pointer as repository");
	}

	@Override
	public Image add(ImageEntity entity) {
		return imageEntityRepository.save(entity);
	}

	@Override
	public Image findById(Integer identifier) {
		final Image entity;

		checkNotNull(identifier, "Received a null pointer as identifier");

		if (imageEntityRepository.existsById(identifier)) {
			entity = imageEntityRepository.getOne(identifier);
		} else {
			entity = new ImageEntity();
		}

		return entity;
	}

	@Override
	public Iterable<ImageEntity> getAllEntities() {
		return imageEntityRepository.findAll();
	}

	@Override
	public Iterable<ImageEntity> getEntities(Pageable page) {
		return imageEntityRepository.findAll(page);
	}

	@Override
	public void remove(ImageEntity imageEntity) {
		imageEntityRepository.delete(imageEntity);

	}

}
