/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package es.udc.fi.dc.fd.controller.user;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.udc.fi.dc.fd.controller.entity.ExampleEntityViewConstants;
import es.udc.fi.dc.fd.model.form.UserForm;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.UserService;

/**
 * Controller for the User entity signup form view.
 * <p>
 * This serves as an adapter between the UI and the services layer.
 * 
 */
@Controller
@RequestMapping("/signup")
public class UserEntitySignupController {

	/**
	 * Example entity service.
	 */
	private final UserService userService;

	@Autowired
	public UserDetailsService userDetailsService;

	/**
	 * Constructs a controller with the specified dependencies.
	 * 
	 * @param service example entity service
	 */
	@Autowired
	public UserEntitySignupController(final UserService service) {
		super();

		userService = checkNotNull(service, "Received a null pointer as service");
	}

	/**
	 * Returns the initial entity form data.
	 * 
	 * @return the initial entity form data
	 */
	@ModelAttribute(ExampleEntityViewConstants.BEAN_FORM)
	public UserForm getUserForm() {
		return new UserForm();
	}

	/**
	 * Persists an entity.
	 * 
	 * @param model         model map
	 * @param form          form data
	 * @param bindingResult binding result
	 * @param response      HTTP response
	 * @return the next view to show
	 */
	@PostMapping
	public String saveUser(final ModelMap model,
			@ModelAttribute(ExampleEntityViewConstants.BEAN_FORM) @Valid final UserForm form,
			final BindingResult bindingResult, final HttpServletRequest request, final HttpServletResponse response) {
		final String path;
		final UserEntity entity;

		if (bindingResult.hasErrors()) {
			// Invalid form data

			// Returns to the form view (TODO: add this to UserViewConstants, change it to
			// something useful)
			// TODO: This is not changing the URI in the navbar
			path = "welcome";

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {

			entity = new UserEntity();
			entity.setLogin(form.getLogin());
			entity.setPassword(form.getPassword());
			entity.setName(form.getName());
			entity.setFirstSurname(form.getFirstSurname());
			entity.setSecondSurname(form.getSecondSurname());
			entity.setCity(form.getCity());

			userService.add(entity);

			UserDetails user = userDetailsService.loadUserByUsername(form.getLogin());

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			path = "welcome";
		}

		return path;
	}

	/**
	 * Shows the user signup view
	 * <p>
	 * Actually it just returns the name of the view. Spring will take care of the
	 * rest.
	 * 
	 */
	@GetMapping
	public String showSignupForm() {

		// TODO: Add this to the UserViewConstants once its merged
		return "user/signup";
	}

	// TODO: Is this needed anymore?
	/**
	 * Loads the model data required for the entities listing view.
	 * <p>
	 * As the view will list all the entities, it requires these entities as one of
	 * the parameters.
	 * 
	 * @param model model map
	 */
	/*
	 * private final void loadViewModel(final ModelMap model) {
	 * model.put(ExampleEntityViewConstants.PARAM_ENTITIES,
	 * exampleEntityService.getAllEntities()); }
	 */

}
