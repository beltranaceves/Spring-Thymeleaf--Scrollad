package es.udc.fi.dc.fc.test.unit.model.persistence;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityServiceImpl;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })

public final class TestAdEntity {
	
	public TestAdEntity() {
        super();
    }
	
	@Autowired
	private AdEntityServiceImpl adEntityservice;
	
	private LocalDateTime date= LocalDateTime.now();
	
	private UserEntity createUserToTest(String username) {
		
		return new UserEntity(username, username, username, username, username, "city", 1, 3.0, 3);
		
	}
	
	private AdEntity createAdToTest(String info) {
		
		UserEntity userEntity = createUserToTest("viewer5"); 
		
		return new AdEntity(info, info, date, 3.0, userEntity, false, false);
		
	}
	
	@Test
	public void addTest() {
		
		Ad adEntity = adEntityservice.add(createAdToTest("anuncio")); 
		
		AdEntity expected = adEntityservice.findById(adEntity.getId());
		
		Double value= expected.getPrice();
		
		
		Assert.assertEquals(expected.getDescription(),adEntity.getDescription());
		Assert.assertEquals(expected.getTitle(),adEntity.getTitle());
		Assert.assertEquals(expected.toString(),adEntity.toString());
		Assert.assertEquals(expected.getDate(),adEntity.getDate());
		Assert.assertEquals(expected.getId(),adEntity.getId());
		Assert.assertEquals(expected.getIsOnHold(),adEntity.getIsOnHold());
		Assert.assertEquals(expected.getIsSold(),adEntity.getIsSold());
		Assert.assertEquals(expected.getPrice(),value);
		Assert.assertEquals(expected.getUserA(),adEntity.getUserA());
		
		
	}

}
