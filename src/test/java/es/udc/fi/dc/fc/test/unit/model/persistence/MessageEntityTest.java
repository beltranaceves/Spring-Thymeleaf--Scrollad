package es.udc.fi.dc.fc.test.unit.model.persistence;

import static org.junit.jupiter.api.Assertions.*;
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

import es.udc.fi.dc.fd.model.persistence.MessageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class MessageEntityTest {
	
	LocalDateTime date= LocalDateTime.now();
	UserEntity sender = new UserEntity();
	UserEntity receiver = new UserEntity();
	
	private MessageEntity getMessageEntity() {
		
		MessageEntity msg = new MessageEntity();
		
		msg.setText("text");
		msg.setReceiver(receiver);
		msg.setSender(sender);
		msg.setDate(date);
		msg.setSeen(false);
		
		return msg;
	}
	
	@Test
	public void testGetters() {
		
		MessageEntity msg = getMessageEntity();
		
		assertEquals(date, msg.getDate());
		assertNull(msg.getId());
		assertEquals(receiver, msg.getReceiver());
		assertEquals(sender, msg.getSender());
		assertEquals(false, msg.isSeen());
		
	}
	
	@Test
	public void testHashCode() {
		
		MessageEntity msg = getMessageEntity();
		
		assertNotEquals(1, msg.hashCode());		
	}
	
	@Test
	public void testEquals() {
		
		MessageEntity msg = getMessageEntity();
		sender.setName("Rosa Melano");
		MessageEntity msg2 = getMessageEntity();
		
		assertTrue(msg.equals(msg2));		
	}
	
	@Test
	public void testToString() {
		
		MessageEntity msg = getMessageEntity();
		
		assertNotEquals("",msg.toString());		
	}
	
	
}