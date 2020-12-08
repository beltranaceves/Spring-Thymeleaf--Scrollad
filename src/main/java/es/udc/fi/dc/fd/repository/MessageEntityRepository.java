package es.udc.fi.dc.fd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.MessageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {

	public Iterable<MessageEntity> findBySenderAndReceiverOrReceiverAndSenderOrderByDateAsc(User sender, User receiver,
			User receiver2, User sender2);

	@Query("SELECT m.receiver FROM Message AS m WHERE m.sender = :user GROUP BY m.receiver ORDER BY m.receiver ASC")
	public Iterable<UserEntity> getReceivers(@Param("user") User user);

	@Query("SELECT m.sender FROM Message AS m WHERE m.receiver = :user GROUP BY m.sender ORDER BY m.sender ASC")
	public Iterable<UserEntity> getSenders(@Param("user") User user);

	public Iterable<MessageEntity> findByReceiver(User receiver);

}
