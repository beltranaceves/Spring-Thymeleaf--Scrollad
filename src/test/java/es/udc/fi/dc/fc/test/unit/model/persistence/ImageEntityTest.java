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
import es.udc.fi.dc.fd.model.Image;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityServiceImpl;
import es.udc.fi.dc.fd.service.image.ImageEntityService;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })


public final class ImageEntityTest {

	public ImageEntityTest() {
        super();
    }
	
	@Autowired
	private ImageEntityService imageEntityService;
	
	@Autowired
	private AdEntityServiceImpl adEntityservice;
	
	@Autowired
	private UserService userService;
	
	private LocalDateTime date= LocalDateTime.now();
	
	private String info= "12345";
	
	private String info2= "1247845";
	
	private byte[] value= new byte[8];
	
	
	private UserEntity createUserToTest(String username) {
		
		return new UserEntity(username, username, username, username, username, "city", 1, 3.0, 3);
		
	}
	
	private AdEntity createAdToTest(String info) {
		
		UserEntity userEntity = createUserToTest("viewer5"); 
		
		User user=userService.add(userEntity);
		
		UserEntity userEntityFind= userService.findById(user.getId());
		
		return new AdEntity(info, info, date, 3.0, userEntityFind, false, false);
		
	}

	@Test
	public void addTest() {
		
		
		Ad adEntity = adEntityservice.add(createAdToTest("anuncio")); 
		
		AdEntity expectedAd = adEntityservice.findById(adEntity.getId());
		
		ImageEntity image = new ImageEntity();
		image.setImageBase64(info);
		image.setAd(expectedAd);
		image.setImage(value);
		image.setAdEntity(expectedAd);
		
		
		Image imageEntity = imageEntityService.add(image);

		Image expected= imageEntityService.findById(imageEntity.getId());
		
		
		Assert.assertEquals(expected.getId(),imageEntity.getId());
		Assert.assertEquals(expected.getImageBase64(),imageEntity.getImageBase64());
		Assert.assertEquals(expected.getAd(),imageEntity.getAd());
		Assert.assertEquals(expected.getImage(),imageEntity.getImage());
		Assert.assertEquals(expected,imageEntity);
		Assert.assertEquals(expected.getClass(),imageEntity.getClass());
		Assert.assertNotEquals(expected,null);
		
		String toString= image.toString();
		
		expected.setImageBase64(info2);
		
		String toString2= image.toString();
		
		
		Assert.assertNotEquals(toString, toString2);
	}
}
