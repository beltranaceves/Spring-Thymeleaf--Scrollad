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
import es.udc.fi.dc.fd.service.user.UserEntityService;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class UserServicePremiumTest {
	
	 /**
     * Service being tested.
     */
	@Autowired
	private UserEntityRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
    /**
     * Default constructor.
     */
    public UserServicePremiumTest() {
        super();
    }

    /**
     * Verifies that the service adds entities into persistence.
     */
    
    private UserEntity createUser(String username, Boolean isPremium) {

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword("password");
		user.setName("name");
		user.setFirstLastname("lastname1");
		user.setSecondLastname("lastname2");
		user.setCity("city");
		user.setIsPremium(isPremium);

		return userRepository.save(user);
	}
    
    @Test
	public void testIsPremium() {
    	Boolean expectedIsPremium = true;
    	
    	UserEntity user = createUser("username1", true);
    	
    	assertEquals(expectedIsPremium, userService.isPremiumUser(user.getId()));
		
	}
    
    @Test
	public void testIsPremiumFalse() {
    	Boolean expectedIsPremium = false;
    	
    	UserEntity user = createUser("username2", false);
    	
    	assertEquals(expectedIsPremium, userService.isPremiumUser(user.getId()));
		
	}
    
    @Test
	public void testUpdateIsPremium() {
    	Boolean expectedIsPremium = false;
    	
    	UserEntity user = createUser("username3", true);
    	
    	userService.updateIsPremiumUserByUserId(user.getId(), false);
    	
    	assertEquals(expectedIsPremium, userService.isPremiumUser(user.getId()));
    	
	}
    

}
