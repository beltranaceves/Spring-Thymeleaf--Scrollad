package es.udc.fi.dc.fd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.MessageEntity;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {

	public Iterable<MessageEntity> findBySenderAndReceiverOrReceiverAndSenderOrderByDateAsc(User sender, User receiver,
			User receiver2, User sender2);

}
