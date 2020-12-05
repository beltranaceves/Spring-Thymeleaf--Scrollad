package es.udc.fi.dc.fd.model.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.udc.fi.dc.fd.model.Message;

@Entity(name = "Message")
@Table(name = "messages")
public class MessageEntity implements Message {

	@Transient
	private static final long serialVersionUID = 1328776989450853491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "text", nullable = false, unique = true)
	private String text = "";

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "sender", nullable = false)
	private UserEntity sender;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver", nullable = false)
	private UserEntity receiver;

	@Column(name = "date", nullable = false, unique = true)
	private LocalDateTime date;

	public MessageEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public UserEntity getSender() {
		return sender;
	}

	public UserEntity getReceiver() {
		return receiver;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setText(String text) {
		this.text = checkNotNull(text, "Received a null pointer as text");
	}

	public void setSender(UserEntity sender) {
		this.sender = checkNotNull(sender, "Received a null pointer as sender");
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = checkNotNull(receiver, "Received a null pointer as receiver");
	}

	public void setDate(LocalDateTime date) {
		this.date = checkNotNull(date, "Received a null pointer as date");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageEntity other = (MessageEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessageEntity [text=" + text + ", sender=" + sender + ", receiver=" + receiver + ", date=" + date + "]";
	}

}