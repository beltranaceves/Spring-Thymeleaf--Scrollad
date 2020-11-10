package es.udc.fi.dc.fd.controller.user;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/followAndUnfollow")
public class UserFollowAndUnfollow {

	private final UserService userService;

	@Autowired
	public UserDetailsService userDetailsService;

	/**
	 * Constructs a controller with the specified dependencies.
	 * 
	 * @param service example entity service
	 */
	@Autowired
	public UserFollowAndUnfollow(final UserService service) {
		super();

		userService = checkNotNull(service, "Received a null pointer as service");
	}

	@GetMapping
	public String getFollowers(final ModelMap model, @RequestParam(value = "username", required = true) String username,
			final HttpServletRequest request, final HttpServletResponse response) {
		final UserEntity userentity = userService.findByUsername(username);
		model.put("user", userentity);
		return "advertisement/list";
	}

	@PostMapping(path = "/follow")
	public void saveFollow(final ModelMap model, @RequestParam(value = "user", required = true) String user,
			@RequestParam(value = "followed", required = true) String followed, final BindingResult bindingResult,
			final HttpServletRequest request, final HttpServletResponse response) {

		final UserEntity userEntity = userService.findByUsername(user);

		if (userEntity.getName().equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Set<String> followers = userEntity.getFollowed();
			followers.add(followed);

			// TODO: No se esta guardando la entidad
			userEntity.setFollowed(followers);
		}
	}

	@PostMapping(path = "/unfollow")
	public void deleteFollow(final ModelMap model, @RequestParam(value = "user", required = true) String user,
			@RequestParam(value = "unfollowed", required = true) String unfollowed, final BindingResult bindingResult,
			final HttpServletRequest request, final HttpServletResponse response) {

		final UserEntity aux = userService.findByUsername(user);

		if (aux.getName().equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Set<String> fol = aux.getFollowed();
			fol.remove(unfollowed);

			// TODO: Lo mismo, no se esta guardando la entidad
			aux.setFollowed(fol);
		}
	}
}
