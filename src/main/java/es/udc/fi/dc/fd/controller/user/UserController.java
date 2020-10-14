package es.udc.fi.dc.fd.controller.user;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.exceptions.IncorrectPasswordException;
import es.udc.fi.dc.fd.model.form.LoginParamsForm;
import es.udc.fi.dc.fd.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	public UserService userService;

	// Login form
	@GetMapping(path = "/login")
	public String login() {
		final String path;
		path = UserViewConstants.USER_LOGIN;
		return path;
	}

	@PostMapping(path = "/login")
	public String login(final ModelMap model,
			@ModelAttribute(UserViewConstants.USER_LOGIN) @Valid final LoginParamsForm form,
			final BindingResult bindingResult, final HttpServletResponse response)
			throws IncorrectLoginException, IncorrectPasswordException {
		final String path;

		if (bindingResult.hasErrors()) {
			// Invalid form data

			// Returns to the form view
			path = UserViewConstants.USER_LOGIN;

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {

			User user = userService.login(form.getLogin(), form.getPassword());

			path = "welcome";

		}
		return path;
	}

	@GetMapping(path = "/logout")
	public String logout() {
		final String path;
		path = "user/logout";
		return path;
	}

}