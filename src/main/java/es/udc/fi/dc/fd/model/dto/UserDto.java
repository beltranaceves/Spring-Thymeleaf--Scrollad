package es.udc.fi.dc.fd.model.dto;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;



public class UserDto {

	
	private String username = "";

	private String password = "";

	private String token = "";


	public UserDto() {
		super();
	}

	public UserDto(String usertoken, String password) {
		super();
		this.username = usertoken;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String value) {
		username = checkNotNull(value, "Received a null pointer as username");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String value) {
		password = checkNotNull(value, "Received a null pointer as password");
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String value) {
		token = checkNotNull(value, "Received a null pointer as token");
	}

	@Override
	public String toString() {
		return "UserDto [usertoken=" + username + ", token=" + token + "]";
	}

	@Override
	public final int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final UserDto other = (UserDto) obj;
		return Objects.equals(username, other.username);
	}

}
