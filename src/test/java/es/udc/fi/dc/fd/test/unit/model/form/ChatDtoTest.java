package es.udc.fi.dc.fd.test.unit.model.form;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.form.ChatDto;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class ChatDtoTest {

	public ChatDtoTest() {
		super();
	}
	
	@Autowired
	private UserService userService;
	
	int unseenMessages = 12;
	
	int unseenMessages2 = 13;
	
	private UserEntity createUserToTest(String username) {
		
		return new UserEntity(username, username, username, username, username, "city", 1, 3.0, 3);
		
	}
	
	@Test
	public void DtoTest() {
		
		UserEntity userEntity = createUserToTest("viewer5"); 
		
		User user=userService.add(userEntity);
		
		UserEntity userFind = userService.findById(user.getId());
		
		ChatDto chatDto= new ChatDto();
		
		chatDto.setUnseenMessages(unseenMessages);
		
		int message= chatDto.getUnseenMessages();
		
		String toString = chatDto.toString();
		
		chatDto.setUnseenMessages(unseenMessages2);
		
		String toString2 = chatDto.toString();
		
		chatDto.setUser(userFind);
		
		ChatDto chatDtoAux= chatDto;
		
		
		
		Assert.assertEquals(message,unseenMessages);
		Assert.assertEquals(chatDto.getUser(),userFind);
		
		Assert.assertNotEquals(toString,toString2);
		
		assertTrue(chatDto.equals(chatDtoAux));		
		assertFalse(chatDto.equals(null));	
		
		Assert.assertNotEquals(chatDto.hashCode(),0);	

	}

}
