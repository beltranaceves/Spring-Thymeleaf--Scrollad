package es.udc.fi.dc.fd.model.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import es.udc.fi.dc.fd.model.persistence.UserEntity;

public final class ChatDto implements Serializable {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1328776989450853491L;

	/**
	 * Name field.
	 * <p>
	 * This is a required field and can't be empty.
	 */
	@NotEmpty
	private UserEntity user;

	@NotEmpty
	private int unseenMessages;

	public ChatDto() {
		super();
	}

	public UserEntity getUser() {
		return user;
	}

	public int getUnseenMessages() {
		return unseenMessages;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setUnseenMessages(int unseenMessages) {
		this.unseenMessages = unseenMessages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + unseenMessages;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		ChatDto other = (ChatDto) obj;
		if (unseenMessages != other.unseenMessages)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChatDto [user=" + user + ", unseenMessages=" + unseenMessages + "]";
	}
}
