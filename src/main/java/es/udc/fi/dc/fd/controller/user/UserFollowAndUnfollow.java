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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.controller.ad.AdEntityViewConstants;
import es.udc.fi.dc.fd.controller.entity.ExampleEntityViewConstants;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.UserRepository;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/followAndUnfollow")
public class UserFollowAndUnfollow {

	private final UserService userService;

	private final UserRepository userRepository;
	
	private final AdEntityService adEntityService;
	

	@Autowired
	public UserDetailsService userDetailsService;

	/**
	 * Constructs a controller with the specified dependencies.
	 * 
	 * @param service example entity service
	 */
	@Autowired
	public UserFollowAndUnfollow(final UserService service, final UserRepository repo, final AdEntityService adService ) {
		super();

		userService = checkNotNull(service, "Received a null pointer as service");
		userRepository = checkNotNull(repo, "received a null pointer as repo");
		adEntityService = checkNotNull(adService, "received a null pointer as repo");
	}

	@GetMapping
	public String getFollowers(final ModelMap model, @RequestParam(value = "username", required = true) String username,
			final HttpServletRequest request, final HttpServletResponse response) {
		final UserEntity userentity = userService.findByUsername(username);
		model.put("user", userentity);
		return "advertisement/list";
	}

	@PostMapping(path = "/follow")
	public String saveFollow(final ModelMap model, @RequestParam(value = "followed", required = true) String followed,
			final HttpServletRequest request, final HttpServletResponse response) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		final UserEntity userEntity = userService.findByUsername(username);

		if (userEntity.getName().equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Set<String> followers = userEntity.getFollowed();
			followers.add(followed);
			userEntity.setFollowed(followers);
			userRepository.save(userEntity);
		}

		return "fragments/goBack";
		
	}

	@PostMapping(path = "/unfollow")
	public String deleteFollow(final ModelMap model,
			@RequestParam(value = "unfollowed", required = true) String unfollowed, final HttpServletRequest request,
			final HttpServletResponse response) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		final UserEntity userEntity = userService.findByUsername(username);

		if (userEntity.getName().equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Set<String> fol = userEntity.getFollowed();
			fol.remove(unfollowed);
			userEntity.setFollowed(fol);
			userRepository.save(userEntity);
		}
		
		
		
		return "fragments/goBack";
	}
	
	
}
