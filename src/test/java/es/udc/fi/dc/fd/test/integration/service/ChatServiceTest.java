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

		int count = 0;

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
			count++;
		}
		assertEquals(list.size(), count);
	}

	@Test
	public void testGetChats() {

		final Message message;
		final Message message4;
		final Message message5;

		final List<UserEntity> list = new ArrayList<UserEntity>();

		User user1 = createUser("username1");
		User user2 = createUser("username2");
		User user3 = createUser("username3");
		User user4 = createUser("username4");

		message = service.send("text message 1", user1, user2);
		service.send("text message 2", user2, user1);
		service.send("text message 3", user1, user2);
		message4 = service.send("text message 4", user1, user3);
		message5 = service.send("text message 5", user4, user1);

		list.add(messageRepository.findById(message.getId()).get().getReceiver());
		list.add(messageRepository.findById(message4.getId()).get().getReceiver());
		list.add(messageRepository.findById(message5.getId()).get().getSender());

		List<UserEntity> users = service.getChats(user1);

		assertEquals(list.size(), users.size());

		for (int i = 0; i < users.size(); i++) {
			assertEquals(list.get(i), users.get(i));
		}
	}

	@Test
	public void testGetUnseenMessages() {

		User user1 = createUser("username1");
		User user2 = createUser("username2");
		User user3 = createUser("username3");

		service.send("text message 1", user1, user2);
		service.send("text message 2", user2, user1);

		service.getAllMessagesBetween(user1, user2);

		service.send("text message 3", user2, user1);
		service.send("text message 4", user2, user1);
		service.send("text message 5", user3, user1);

		int unseenSenderIsUser2 = service.getUnseenMessages(user1, user2);
		int unseenSenderIsUser3 = service.getUnseenMessages(user1, user3);

		assertEquals(2, unseenSenderIsUser2);
		assertEquals(1, unseenSenderIsUser3);
	}

	@Test
	public void testFindById() {
		User user1 = createUser("username1");
		User user2 = createUser("username2");
		User user3 = createUser("username3");

		Message message = service.send("text message 1", user1, user2);
		MessageEntity res = service.findById(message.getId());
		assertEquals(message.getId(), res.getId());

	}

	@Test
	public void testFindByIdInexistentMessage() {
		User user1 = createUser("username1");
		User user2 = createUser("username2");
		User user3 = createUser("username3");

		MessageEntity res = service.findById(123);
		assertEquals(null, res.getId());
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
