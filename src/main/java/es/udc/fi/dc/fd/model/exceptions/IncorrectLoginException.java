package es.udc.fi.dc.fd.model.exceptions;

@SuppressWarnings("serial")
public class IncorrectLoginException extends Exception {

	private String login;
	private String password;

	public IncorrectLoginException(String login, String password) {

		this.login = login;
		this.password = password;

	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

}