package es.udc.fi.dc.fd.model.form;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public class UserConversor {

	private UserConversor() {
	}

	public final static UserForm toUserForm(User user) {
		return new UserForm(user.getId(), user.getLogin(), user.getName(), user.getFirstSurname(),
				user.getSecondSurname(), user.getCity());
	}

	public final static UserEntity toUser(UserForm userForm) {

		return new UserEntity(userForm.getLogin(), userForm.getPassword(), userForm.getName(),
				userForm.getFirstSurname(), userForm.getSecondSurname(), userForm.getCity());
	}

	public final static AuthenticatedUserForm toAuthenticatedUserForm(String serviceToken, User user) {

		return new AuthenticatedUserForm(serviceToken, toUserForm(user));

	}

}
