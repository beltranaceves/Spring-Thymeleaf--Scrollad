package es.udc.fi.dc.fd.model.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticatedUserForm {

	private String serviceToken;
	private UserForm userForm;

	public AuthenticatedUserForm() {
	}

	public AuthenticatedUserForm(String serviceToken, UserForm userForm) {

		this.serviceToken = serviceToken;
		this.userForm = userForm;
	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	@JsonProperty("user")
	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

}
