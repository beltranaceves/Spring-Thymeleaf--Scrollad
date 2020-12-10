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
import es.udc.fi.dc.fd.model.persistence.OrderEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.AdEntityRepository;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.order.OrderService;

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
public class OrderServiceTest {

	/**
	 * Service being tested.
	 */
	@Autowired
	private UserEntityRepository userRepository;
	
	@Autowired
	private AdEntityRepository adRepository;

	@Autowired
	private OrderService orderService;

	/**
	 * Default constructor.
	 */
	public OrderServiceTest() {
		super();
	}
	
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
	
	private OrderEntity createOrder(UserEntity user, AdEntity ad) {

		OrderEntity order = new OrderEntity();
		
		order.setAd(ad);
		order.setAddress("Avd de Arteixo");
		order.setCreditCard("1234566");
		order.setDate(LocalDateTime.now());
		order.setPrice(10.61);
		order.setUser(user);	
		
		return order;
	}
	
	@Test
	public void testAddOrder() {

		UserEntity user = createUser("username1");

		AdEntity ad = createAd(userRepository.getOne(user.getId()), "title");
		
		OrderEntity order = createOrder(user, ad);
		
		orderService.addOrder(order);
		
		OrderEntity actual = orderService.findById(order.getId());
		
		assertEquals(order, actual);
		
	}
	
	@Test
	public void testGetEntitiesByUser() {
		
		int count = 0;
		
		UserEntity user = createUser("username1");

		AdEntity ad1 = createAd(userRepository.getOne(user.getId()), "title1");
		AdEntity ad2 = createAd(userRepository.getOne(user.getId()), "title2");
		
		List<OrderEntity> expected = new ArrayList<OrderEntity>();
		
		OrderEntity order1 = createOrder(user, ad1);
		OrderEntity order2 = createOrder(user, ad2);
		expected.add(order1);
		expected.add(order2);
		
		orderService.addOrder(order1);
		orderService.addOrder(order2);
		
		Iterable<OrderEntity> result = orderService.getEntitiesByUser(user);
		
		for (OrderEntity order : result) {
			assertEquals(expected.get(count), order);
			count++;
		}
	}
	
	
}
