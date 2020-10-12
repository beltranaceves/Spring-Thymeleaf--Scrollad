package es.udc.fi.dc.fd.model.form;

public class JwtInfo {

	private Integer id;
	private String login;

	public JwtInfo(Integer id, String login) {

		this.id = id;
		this.login = login;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}