package es.udc.fi.dc.fd.test.unit.model.form;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.dto.AdEntityDto;
import es.udc.fi.dc.fd.model.form.OrderForm;
import es.udc.fi.dc.fd.model.form.UserForm;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class OrderFormTest {

	public OrderFormTest() {
		super();
	}

	@Test
	public void FormTest() {
		OrderForm orderForm = new OrderForm();
		
		String address = "Manuel Murguia n16, 5o E";
		orderForm.setAddress(address);
		
		Integer adId = 1;
		orderForm.setAdId(adId);
		
		String creditCard = "1234567891234567";
		orderForm.setCreditCard(creditCard);
		
		
		assertEquals(orderForm.getAddress(), address);
		assertEquals(orderForm.getAdId(), adId);
		assertEquals(orderForm.getCreditCard(), creditCard);
		
		OrderForm orderForm2 = new OrderForm();
		
		address = "Manuel Murguia n16, 1o A";
		orderForm2.setAddress(address);
		
		adId = 2;
		orderForm2.setAdId(adId);
		
		creditCard = "98765432198";
		orderForm2.setCreditCard(creditCard);
		
		
		assertNotEquals(orderForm.toString(), orderForm2.toString());
		
		assertEquals(orderForm, orderForm);
		
		assertNotEquals(orderForm, orderForm2);
		assertNotEquals(orderForm.hashCode(), orderForm2.hashCode());
		
		
		orderForm.setAdId(null);
		assertNotEquals(orderForm, orderForm2);
		assertNotEquals(orderForm, null);
		assertNotEquals(orderForm, 1);
		
		orderForm.setAddress(null);
		assertNotEquals(orderForm, orderForm2);
		
		orderForm.setAddress("dasdasd");
		assertNotEquals(orderForm, orderForm2);
		
	}


}
