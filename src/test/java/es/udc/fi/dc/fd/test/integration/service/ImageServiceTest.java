package es.udc.fi.dc.fd.test.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.Image;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.AdEntityRepository;
import es.udc.fi.dc.fd.repository.ImageEntityRepository;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.image.ImageEntityService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class ImageServiceTest {

	public ImageServiceTest() {

	}

	@Autowired
	private ImageEntityService imageService;

	@Autowired
	private ImageEntityRepository imageRepository;

	@Autowired
	private AdEntityRepository adRepository;

	@Autowired
	private UserEntityRepository userRepository;

	@Test
	public void addTest() {

		UserEntity user = createUser("login");
		AdEntity ad = createAd(user, "title");
		ImageEntity image = createImage(ad);

		Image expected = imageService.add(image);

		assertEquals(expected, image);
	}

	@Test
	public void findById() {

		UserEntity user = createUser("login");
		AdEntity ad = createAd(user, "title");
		ImageEntity image = createImage(ad);

		Image obtained = imageService.findById(image.getId());

		assertEquals(image, obtained);
	}

	@Test
	public void getAllEntities() {

		UserEntity user = createUser("login");
		AdEntity ad = createAd(user, "title");
		ImageEntity image = createImage(ad);

		List<ImageEntity> images = new ArrayList<ImageEntity>();
		images.add(image);

		List<ImageEntity> result = (List<ImageEntity>) imageService.getAllEntities();
		assertEquals(images.get(0), result.get(0));
	}

	@Test
	public void remove() {

		UserEntity user = createUser("login");
		AdEntity ad = createAd(user, "title");
		ImageEntity image = createImage(ad);

		imageService.remove(image);
		assertEquals(0, imageRepository.count());
	}

	private ImageEntity createImage(AdEntity ad) {

		ImageEntity entity = new ImageEntity();
		byte[] bytes = ("imagen").getBytes();

		entity.setImage(bytes);
		entity.setAd(ad);

		return imageRepository.save(entity);
	}

	private UserEntity createUser(String username) {

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword("password");
		user.setName("name");
		user.setFirstLastname("lastname1");
		user.setSecondLastname("lastname2");
		user.setCity("city");
		user.setScoreCount(1);
		user.setAverageScore(3.0);
		user.setSumScore(3);

		return userRepository.save(user);
	}

	private AdEntity createAd(UserEntity user, String title) {

		AdEntity ad = new AdEntity();

		ad.setTitle(title);
		ad.setDescription("description");
		ad.setPrice(10.5);
		ad.setDate(LocalDateTime.now());
		ad.setUserA(user);
		ad.setIsOnHold(false);

		List<ImageEntity> images = new ArrayList<ImageEntity>();
		ad.setImages(images);

		return adRepository.save(ad);
	}
}
