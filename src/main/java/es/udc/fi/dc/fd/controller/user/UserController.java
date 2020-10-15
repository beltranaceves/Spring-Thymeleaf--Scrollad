package es.udc.fi.dc.fd.controller.user;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.udc.fi.dc.fd.model.form.LoginParamsForm;

@Controller
@RequestMapping("/login")
public class UserController {

	@Autowired
	public UserDetailsService userDetailsService;

	// Login form
	@GetMapping
	public String login() {
		final String path;
		path = UserViewConstants.USER_LOGIN;
		return path;
	}

	@PostMapping
	public String login(final ModelMap model,
			@ModelAttribute(UserViewConstants.USER_LOGIN) @Valid final LoginParamsForm form,
			final BindingResult bindingResult, final HttpServletResponse response) {
		final String path;

		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			// Invalid form data

			// Returns to the form view
			path = UserViewConstants.USER_LOGIN;

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {

			UserDetails user = userDetailsService.loadUserByUsername(form.getLogin());

			path = "welcome";

		}

		return path;
	}

}