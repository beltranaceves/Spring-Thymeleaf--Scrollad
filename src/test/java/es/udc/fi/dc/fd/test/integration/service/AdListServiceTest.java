package es.udc.fi.dc.fd.test.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.dto.AdEntityDto;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.AdEntityRepository;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.chat.ChatService;

/**
 * Integration tests for the {@link ChatService}.
 * <p>
 * As this service doesn't contain any actual business logic, and it just wraps
 * the example entities repository, these tests are for verifying everything is
 * set up correctly and working.
 */

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class AdListServiceTest {

	/**
	 * Service being tested.
	 */
	@Autowired
	private AdEntityService service;

	@Autowired
	private UserEntityRepository userRepository;

	@Autowired
	private AdEntityRepository adRepository;

	/**
	 * Default constructor.
	 */
	public AdListServiceTest() {
		super();
	}

	@Test
	public void testFindAdById() {

		int count = 0;

		User user1 = createUser("username1");
		String keywords = "title";

		List<AdEntity> expected = new ArrayList<AdEntity>();

		AdEntity ad1 = createAd(userRepository.getOne(user1.getId()), "title");
		AdEntity ad2 = createAd(userRepository.getOne(user1.getId()), "longer title");
		AdEntity ad3 = createAd(userRepository.getOne(user1.getId()), "title2");

		expected.add(ad1);
		expected.add(ad2);
		expected.add(ad3);

		AdEntity ad = service.findById(ad1.getId());
		
		AdEntityDto adEntityDto = new AdEntityDto();
		String stringDate = "2020-10-10T18:34:34.043";
		String description = "Esta es la descripcion";
		Integer integer = 12;
		List<ImageEntity>  images = new ArrayList<ImageEntity>();
		Boolean isOnHold = false;
		Boolean isSold = false;
		Double price = 10.0;
		String title = "Test";	
		UserEntity userA = new UserEntity();
		AdEntity adEntity = new AdEntity(title, description, LocalDateTime.now(), price, userA, isOnHold, isSold);
		
		service.add(adEntity);
		service.findById(adEntity.getId());
		service.remove(adEntity);
		
		assertEquals(ad1.getId(), ad.getId());

	}

	@Test
	public void testUpdateIsOnHold() {

		int count = 0;

		User user1 = createUser("username1");
		String keywords = "title";

		List<AdEntity> expected = new ArrayList<AdEntity>();

		AdEntity ad1 = createAd(userRepository.getOne(user1.getId()), "title");
		
		service.updateIsOnHoldById(ad1.getId());

		AdEntity ad = service.findById(ad1.getId());
		
		
		List<AdEntity> adList = service.getAllEntities();
		
		assertEquals(ad.getIsOnHold(), true);

	}

	private User createUser(String username) {

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
