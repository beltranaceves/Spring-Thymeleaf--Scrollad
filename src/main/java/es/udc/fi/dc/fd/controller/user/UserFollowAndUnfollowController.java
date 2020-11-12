package es.udc.fi.dc.fd.controller.user;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.controller.ad.AdEntityViewConstants;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/followAndUnfollow")
public class UserFollowAndUnfollowController {

	private final UserService userService;

	private final UserEntityRepository userRepository;

	private final AdEntityService adEntityService;

	@Autowired
	public UserDetailsService userDetailsService;

	/**
	 * Constructs a controller with the specified dependencies.
	 * 
	 * @param service example entity service
	 */
	@Autowired
	public UserFollowAndUnfollowController(final UserService service, final UserEntityRepository repo,
			final AdEntityService adService) {
		super();

		userService = checkNotNull(service, "Received a null pointer as service");
		userRepository = checkNotNull(repo, "received a null pointer as repo");
		adEntityService = checkNotNull(adService, "received a null pointer as repo");
	}

	@PostMapping(path = "/follow")
	public String saveFollow(final ModelMap model, @RequestParam(value = "followed", required = true) String followed,
			@RequestParam(value = "returnUri", required = true) String returnstring, final HttpServletRequest request,
			final HttpServletResponse response) {

		// Get current user info from SecurityContextHolder
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		final UserEntity userEntity = userService.findByUsername(username);

		// If no user bad request, else follow or unfollow accordingly.
		if (userEntity.getName().equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Set<String> followers = userEntity.getFollowed();
			if (followers.contains(followed)) {
				followers.remove(followed);
				userEntity.setFollowed(followers);
				userRepository.save(userEntity);
			} else if (!followers.contains(followed)) {
				followers.add(followed);
				userEntity.setFollowed(followers);
				userRepository.save(userEntity);
			}
		}
		model.put("user", userEntity);
		model.put("follows", userEntity.getFollowed());
		model.put(AdEntityViewConstants.PARAM_ENTITIES, adEntityService.getAllEntities());
		if (returnstring.contains("list")) {
			return AdEntityViewConstants.VIEW_ENTITY_LIST;
		} else if (returnstring.contains("followed")) {
			return AdEntityViewConstants.VIEW_FOLLOWED_ENTITY_LIST;
		}
		return AdEntityViewConstants.VIEW_ENTITY_LIST;
	}
}
