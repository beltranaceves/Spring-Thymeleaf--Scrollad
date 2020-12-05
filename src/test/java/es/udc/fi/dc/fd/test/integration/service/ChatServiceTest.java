package es.udc.fi.dc.fd.test.integration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import es.udc.fi.dc.fd.model.Message;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.MessageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.MessageEntityRepository;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.chat.ChatService;

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
public class ChatServiceTest {

	/**
	 * Service being tested.
	 */
	@Autowired
	private ChatService service;

	@Autowired
	private UserEntityRepository userRepository;

	@Autowired
	private MessageEntityRepository messageRepository;

	/**
	 * Default constructor.
	 */
	public ChatServiceTest() {
		super();
	}

	@Test
	public void testSendMessage() {

		final Message message;

		User user1 = createUser("username1");
		User user2 = createUser("username2");

		message = service.send("text", user1, user2);

		assertEquals("text", message.getText());
		assertEquals(user1, message.getSender());
		assertEquals(user2, message.getReceiver());

	}

	@Test
	public void testGetMessages() {

		final Message message;
		final Message message2;
		final Message message3;
		final Message message4;

		final List<MessageEntity> list = new ArrayList<MessageEntity>();

		User user1 = createUser("username1");
		User user2 = createUser("username2");

		message = service.send("text message 1", user1, user2);
		message2 = service.send("text message 2", user2, user1);
		message3 = service.send("text message 3", user1, user2);
		message4 = service.send("text message 4", user2, user1);

		list.add(messageRepository.findById(message.getId()).get());
		list.add(messageRepository.findById(message2.getId()).get());
		list.add(messageRepository.findById(message3.getId()).get());
		list.add(messageRepository.findById(message4.getId()).get());

		Iterable<MessageEntity> messages = service.getAllMessagesBetween(user1, user2);

		for (MessageEntity messageEntity : messages) {
			assertTrue(list.contains(messageEntity));
		}
	}

	private User createUser(String username) {

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword("password");
		user.setName("name");
		user.setFirstLastname("lastname1");
		user.setSecondLastname("lastname2");
		user.setCity("city");

		return userRepository.save(user);
	}

}
