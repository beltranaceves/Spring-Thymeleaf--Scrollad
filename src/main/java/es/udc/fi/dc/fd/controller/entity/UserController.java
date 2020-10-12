package es.udc.fi.dc.fd.controller.entity;

import static es.udc.fi.dc.fd.model.form.UserConversor.toAuthenticatedUserForm;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.form.AuthenticatedUserForm;
import es.udc.fi.dc.fd.model.form.JwtInfo;
import es.udc.fi.dc.fd.model.form.LoginParamsForm;
import es.udc.fi.dc.fd.service.JwtGenerator;
import es.udc.fi.dc.fd.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public AuthenticatedUserForm login(@Validated @RequestBody LoginParamsForm params) throws IncorrectLoginException {

		User user = userService.login(params.getLogin(), params.getPassword());

		return toAuthenticatedUserForm(generateServiceToken(user), user);

	}

	@PostMapping("/loginFromServiceToken")
	public AuthenticatedUserForm loginFromServiceToken(@RequestAttribute Integer login,
			@RequestAttribute String serviceToken) throws InstanceNotFoundException {

		User user = userService.loginFromId(login);

		return toAuthenticatedUserForm(serviceToken, user);

	}

	private String generateServiceToken(User user) {

		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getLogin());

		return jwtGenerator.generate(jwtInfo);

	}

}
