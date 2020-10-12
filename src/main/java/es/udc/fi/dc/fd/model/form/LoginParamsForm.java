package es.udc.fi.dc.fd.model.form;

import javax.validation.constraints.NotNull;

public class LoginParamsForm {

	private String login;
	private String password;

	public LoginParamsForm() {
	}

	@NotNull
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotNull
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
