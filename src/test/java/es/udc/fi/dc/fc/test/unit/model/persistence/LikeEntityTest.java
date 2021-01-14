package es.udc.fi.dc.fc.test.unit.model.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.LikeEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class LikeEntityTest {
	
	UserEntity user = new UserEntity();
	UserEntity user2 = new UserEntity();
	AdEntity ad = new AdEntity();
	
	private LikeEntity getLikeEntity(UserEntity usr, AdEntity adE) {
		
		LikeEntity like = new LikeEntity(usr, adE);
		
		like.setId(1);
		like.setAdLiked(ad);
		like.setUser(user);
		
		return like;
	}
	
	@Test
	public void testGetters() {
		
		LikeEntity entity = getLikeEntity(user, ad);
		
		assertEquals(ad, entity.getAdLiked());
		assertEquals(1, entity.getId());
		assertEquals(user, entity.getUser());
		
	}
	
	@Test
	public void testHashCode() {
		
		LikeEntity entity = getLikeEntity(user, ad);
		
		assertNotEquals(1, entity.hashCode());		
	}
	
	@Test
	public void testEquals() {
		
		LikeEntity entity = getLikeEntity(user, ad);
		user2.setName("Rosa Melano");
		LikeEntity entity2 = getLikeEntity(user2, ad);
		
		assertTrue(entity.equals(entity2));		
	}

}
