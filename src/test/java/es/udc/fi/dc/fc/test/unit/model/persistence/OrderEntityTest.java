package es.udc.fi.dc.fc.test.unit.model.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class OrderEntityTest {

	UserEntity user = new UserEntity();
	UserEntity user2 = new UserEntity();
	AdEntity ad = new AdEntity();
	
	private OrderEntity getOrderEntity() {
		
		OrderEntity order = new OrderEntity();
		
		order.setId(1);
		order.setAd(ad);
		order.setUser(user);
		order.setAddress("dfw");
		order.setDate(LocalDateTime.now());
		order.setPrice(1.1);
		order.setCreditCard("123");		
		return order;
	}
	
	@Test
	public void testGetters() {
		
		OrderEntity entity = getOrderEntity();
		
		assertEquals(ad, entity.getAd());
		assertEquals(1, entity.getId());
		assertEquals(user, entity.getUser());
		assertEquals("dfw", entity.getAddress());
		assertEquals(LocalDateTime.now(), entity.getDate());
		assertEquals(1.1, entity.getPrice());
		assertEquals("123", entity.getCreditCard());
		
	}
	
	@Test
	public void testHashCode() {
		
		OrderEntity entity = getOrderEntity();
		
		assertNotEquals(1, entity.hashCode());		
	}
	
	@Test
	public void testEquals() {
		
		OrderEntity entity = getOrderEntity();
		user2.setName("Rosa Melano");
		OrderEntity entity2 = getOrderEntity();
		
		assertTrue(entity.equals(entity2));		
	}
}
