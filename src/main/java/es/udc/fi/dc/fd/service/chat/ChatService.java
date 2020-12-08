package es.udc.fi.dc.fd.service.chat;

import java.util.List;

import es.udc.fi.dc.fd.model.Message;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.MessageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface ChatService {

	public Message send(final String message, final User user1, final User user2);

	public MessageEntity findById(final Integer identifier);

	public Iterable<MessageEntity> getAllMessagesBetween(final User loggedUser, final User vendor);

	public List<UserEntity> getChats(User user);

	public Integer getUnseenMessages(User receiver, User sender);

}
