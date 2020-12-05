package es.udc.fi.dc.fd.service.chat;

import es.udc.fi.dc.fd.model.Message;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.MessageEntity;

public interface ChatService {

	public Message send(final String message, final User user1, final User user2);

	public MessageEntity findById(final Integer identifier);

	public Iterable<MessageEntity> getAllMessagesBetween(final User user1, final User user2);

}
