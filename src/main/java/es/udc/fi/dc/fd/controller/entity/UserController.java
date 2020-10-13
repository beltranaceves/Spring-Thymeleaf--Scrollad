package es.udc.fi.dc.fd.controller.entity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

	// Login form
	@GetMapping(path = "/login")
	public String login() {
		final String path;
		path = ExampleEntityViewConstants.USER_LOGIN;
		return path;
	}

	@GetMapping(path = "/logged")
	public String logged() {
		final String path;
		path = "user/logged";
		return path;
	}

	// Login form with error
	@RequestMapping("/login-error.html")
	public String loginError(ModelMap model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

//	@PostMapping("/login")
//	public AuthenticatedUserForm loginUser(final ModelMap model,
//			@ModelAttribute(ExampleEntityViewConstants.BEAN_FORM) @Valid @RequestBody LoginParamsForm form,
//			final BindingResult bindingResult, final HttpServletResponse response) throws IncorrectLoginException {
//
//		User user = userService.login(form.getLogin(), form.getPassword());
//
//		return UserConversor.toAuthenticatedUserForm(generateServiceToken(user), user);
//	}
//
//	@PostMapping("/login")
//	public AuthenticatedUserForm login(@Validated @RequestBody LoginParamsForm params) throws IncorrectLoginException {
//
//		User user = userService.login(params.getLogin(), params.getPassword());
//
//		return UserConversor.toAuthenticatedUserForm(generateServiceToken(user), user);
//
//	}
//
//	@PostMapping("/loginFromServiceToken")
//	public AuthenticatedUserForm loginFromServiceToken(@RequestAttribute Integer login,
//
//			@RequestAttribute String serviceToken) throws InstanceNotFoundException {
//
//		User user = userService.loginFromId(login);
//
//		return UserConversor.toAuthenticatedUserForm(serviceToken, user);
//
//	}
//
//	private String generateServiceToken(User user) {
//
//		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getLogin());
//
//		return jwtGenerator.generate(jwtInfo);
//
//	}

}
