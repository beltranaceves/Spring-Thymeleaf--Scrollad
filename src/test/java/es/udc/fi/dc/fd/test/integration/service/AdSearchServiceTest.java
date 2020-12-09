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

import es.udc.fi.dc.fd.model.User;
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
public class AdSearchServiceTest {

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
	public AdSearchServiceTest() {
		super();
	}

	@Test
	public void testFindAd() {

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

		Iterable<AdEntity> result = service.findAds("city", keywords.trim(), null, 3.0, 0.0, 20.0);

		for (AdEntity ad : result) {
			assertEquals(expected.get(count), ad);
			count++;
		}

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
