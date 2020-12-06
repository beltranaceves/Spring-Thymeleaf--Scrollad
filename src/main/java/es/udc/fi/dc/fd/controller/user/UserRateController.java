package es.udc.fi.dc.fd.controller.user;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.controller.ad.AdEntityViewConstants;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.UserEntityRepository;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserRateController {

	private final UserService userService;

	private final UserEntityRepository userRepository;

	private final AdEntityService adEntityService;

	private final LikeService likedAdService;
	
	@Autowired
	public UserRateController(final UserService service, final UserEntityRepository repo,
			final AdEntityService adService, final LikeService likeService) {
		super();

		userService = checkNotNull(service, "Received a null pointer as service");
		userRepository = checkNotNull(repo, "received a null pointer as repo");
		adEntityService = checkNotNull(adService, "received a null pointer as repo");
		likedAdService = checkNotNull(likeService, "Received a null pointer as service");
	}
	
	@PostMapping(path = "/score")
	public String saveScore(final ModelMap model, @RequestParam(value = "rated", required = true) String rated,
			@RequestParam(value = "score", required = true) Integer score,
			@RequestParam(value = "returnUri", required = true) String returnstring,
			final HttpServletRequest request, final HttpServletResponse response) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		Integer count;
		Integer sumScore;
		Double average;
		
		
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		final UserEntity userLoged = userService.findByUsername(username);
		final UserEntity ratedUser = userService.findByUsername(rated);
		
		
		if (userLoged.getName().equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			Set<String> scoredUserList = userLoged.getScored();
			if (!scoredUserList.contains(rated)) {
				scoredUserList.add(rated);
				userLoged.setScored(scoredUserList);
				userRepository.save(userLoged);
				
				count= ratedUser.getScoreCount();
				count = count + 1;
				ratedUser.setScoreCount(count);
				userRepository.save(ratedUser);
				
				sumScore= ratedUser.getSumScore();
				sumScore= sumScore + score;
				ratedUser.setSumScore(sumScore);
				userRepository.save(ratedUser);
				
				average= ratedUser.getAverageScore();
				average= Double.valueOf(sumScore)/count;
				ratedUser.setAverageScore(average);
				userRepository.save(ratedUser);
			}
			
		}
		Iterable<AdEntity> likedAds = likedAdService.getAdsLikedByUser(userLoged);
		List<Integer> likesList = new ArrayList<>();
		likedAds.forEach(likedAd -> {
			likesList.add(likedAd.getId());
		});
		model.addAttribute("likesList", likesList);
		model.put("user", userLoged);
		model.put("scoredUsers", userLoged.getScored());
		model.put("follows", userLoged.getFollowed());
		model.put(AdEntityViewConstants.PARAM_ENTITIES, adEntityService.getAllEntities());
		if (returnstring.contains("list")) {
			return AdEntityViewConstants.VIEW_ENTITY_LIST;
		}
		
		return AdEntityViewConstants.VIEW_ENTITY_LIST;
	}
	
	
}