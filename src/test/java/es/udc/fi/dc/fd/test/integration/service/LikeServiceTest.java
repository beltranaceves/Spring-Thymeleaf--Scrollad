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

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.AdEntityRepository;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.like.LikeService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class LikeServiceTest {
	
	 /**
     * Service being tested.
     */
	@Autowired
	private UserEntityRepository userRepository;
	
	@Autowired
	private AdEntityRepository adRepository;
	
	@Autowired
    private LikeService likeService;
	
    /**
     * Default constructor.
     */
    public LikeServiceTest() {
        super();
    }

    /**
     * Verifies that the service adds entities into persistence.
     */
    
    private UserEntity createUser(String username) {

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword("password");
		user.setName("name");
		user.setFirstLastname("lastname1");
		user.setSecondLastname("lastname2");
		user.setCity("city");

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
    
    @Test
	public void testAddLikeAndGetLikes() {
    	
    	int count = 0;
    	
    	UserEntity user = createUser("username1");

		AdEntity ad1 = createAd(userRepository.getOne(user.getId()), "title1");
		AdEntity ad2 = createAd(userRepository.getOne(user.getId()), "title2");
		
		likeService.addLike(user, ad1);
		likeService.addLike(user, ad2);

		List<AdEntity> adsExpected = new ArrayList<AdEntity>();
		adsExpected.add(ad1);
		adsExpected.add(ad2);

		
		Iterable<AdEntity> result = likeService.getAdsLikedByUser(user);
		
		for (AdEntity ad : result) {
			assertEquals(adsExpected.get(count), ad);
			count++;
		}
		
	}
    
    @Test
   	public void deleteByAdLikedIdAndUserId() {
       	
    	int count = 0;
      	
       	UserEntity user = createUser("username1");

   		AdEntity ad1 = createAd(userRepository.getOne(user.getId()), "title1");
   		AdEntity ad2 = createAd(userRepository.getOne(user.getId()), "title2");
   		
   		likeService.addLike(user, ad1);
   		likeService.addLike(user, ad2);

   		List<AdEntity> adsExpected = new ArrayList<AdEntity>();
   		adsExpected.add(ad2);

   		likeService.deleteByAdLikedIdAndUserId(ad1.getId(), user.getId());
   		
   		Iterable<AdEntity> result = likeService.getAdsLikedByUser(user);
   		
   		for (AdEntity ad : result) {
   			assertEquals(adsExpected.get(count), ad);
   			count++;
   		}
   		
   	}
    
    

}
