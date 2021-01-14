package es.udc.fi.dc.fd.test.unit.model.dto;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class AdEntityDtoTest {

	public AdEntityDtoTest() {
		super();
	}

	@Test
	public void stringDateTest() {
		AdEntityDto adEntityDto = new AdEntityDto();
		
		String stringDate = "2020-10-10T18:34:34.043";
		adEntityDto.setDate(stringDate);
		
		String description = "Esta es la descripcion";
		adEntityDto.setDescription(description);
		
		Integer integer = 12;
		adEntityDto.setId(integer);
		
		List<ImageEntity>  images = new ArrayList<ImageEntity>();
		adEntityDto.setImages(images);
		
		Boolean isOnHold = false;
		adEntityDto.setIsOnHold(isOnHold);
		
		Boolean isSold = false;
		adEntityDto.setIsSold(isSold);
		
		Double price = 10.0;
		adEntityDto.setPrice(price);
		
		String title = "Test";
		adEntityDto.setTitle(title);
		
		UserEntity userA = new UserEntity();
		adEntityDto.setUserA(userA);

		AdEntityDto adEntityDto2 = new AdEntityDto(images, integer + 1, title, description, LocalDateTime.now(), price, userA, isOnHold, isSold);
		
		assertEquals(adEntityDto.getDate(), "2020-10-10 18:34");
		assertEquals(adEntityDto.getDescription(), description);
		assertEquals(adEntityDto.getId(), integer);
		assertEquals(adEntityDto.getImages(), images);
		assertEquals(adEntityDto.getIsOnHold(), isOnHold);
		assertEquals(adEntityDto.getIsSold(), isSold);
		assertEquals(adEntityDto.getPrice(), price);
		assertEquals(adEntityDto.getTitle(), title);
		assertEquals(adEntityDto.getUserA(), userA);
		assertNotEquals(adEntityDto.toString(), adEntityDto2.toString());
		assertNotEquals(adEntityDto.hashCode(), adEntityDto2.hashCode());
		assertEquals(adEntityDto.getClass(), adEntityDto2.getClass());
		assertFalse(adEntityDto.equals(adEntityDto2));
	}


}
