package es.udc.fi.dc.fd.service.chat;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.Message;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.MessageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.MessageEntityRepository;
import es.udc.fi.dc.fd.repository.UserEntityRepository;

@Service
public class ChatServiceImpl implements ChatService {

	private final MessageEntityRepository messageRepository;

	private final UserEntityRepository userRepository;

	@Autowired
	public ChatServiceImpl(final MessageEntityRepository repository, final UserEntityRepository repository2) {
		super();

		messageRepository = checkNotNull(repository, "Received a null pointer as repository");

		userRepository = checkNotNull(repository2, "Received a null pointer as repository");

	}

	@Override
	public Message send(String text, User user1, User user2) {

		final UserEntity sender;
		final UserEntity receiver;

		Optional<UserEntity> obtainedSender = userRepository.findById(user1.getId());

		Optional<UserEntity> obtainedReceiver = userRepository.findById(user2.getId());

		if (obtainedSender.isPresent()) {
			sender = obtainedSender.get();

		} else {
			sender = new UserEntity();
		}
		if (obtainedReceiver.isPresent()) {
			receiver = obtainedReceiver.get();
		} else {
			receiver = new UserEntity();
		}

		MessageEntity message = new MessageEntity();

		message.setText(text);
		message.setDate(LocalDateTime.now());
		message.setSender(sender);
		message.setReceiver(receiver);

		return messageRepository.save(message);
	}

	@Override
	public MessageEntity findById(Integer identifier) {

		final MessageEntity message;

		Optional<MessageEntity> obtainedMessage = messageRepository.findById(identifier);

		if (obtainedMessage.isPresent()) {
			message = obtainedMessage.get();
		} else {
			message = new MessageEntity();
		}

		return message;
	}

	@Override
	public Iterable<MessageEntity> getAllMessagesBetween(User user1, User user2) {

		return messageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByDateAsc(user1, user2, user1, user2);
	}

	@Override
	public List<UserEntity> getChats(User user) {

		final List<UserEntity> users = new ArrayList<UserEntity>();

		Iterable<UserEntity> receivers = messageRepository.getReceivers(user);
		receivers.forEach(r -> {
			users.add(r);
		});

		Iterable<UserEntity> senders = messageRepository.getSenders(user);
		senders.forEach(s -> {
			users.add(s);
		});

		return users.stream().distinct().collect(Collectors.toList());
	}

}
