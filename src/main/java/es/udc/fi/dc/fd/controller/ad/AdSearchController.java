package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/advertisement")
public class AdSearchController {

	private final AdEntityService adEntityService;
	private final LikeService likedAdService;
	private final UserService userEntityService;

	@Autowired
	public AdSearchController(final LikeService likeService, final AdEntityService service,
			final UserService userService) {
		super();
		likedAdService = checkNotNull(likeService, "Received a null pointer as service");
		adEntityService = checkNotNull(service, "Received a null pointer as service");
		userEntityService = checkNotNull(userService, "Received a null pointer as service");

	}

	public UserEntity getLoggedUser(final ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userEntityService.findByUsername(auth.getName());

		return user;
	}

	@GetMapping(path = "/search")
	public String findAds(@RequestParam(required = false, value = "city") String city,
			@RequestParam(required = false, value = "keywords") String keywords,
			@RequestParam(required = false, value = "interval") String interval,
			@RequestParam(required = false, value = "minPrice") Double minPrice,
			@RequestParam(required = false, value = "maxPrice") Double maxPrice, final ModelMap model) {

		loadViewModel(model, city, keywords, interval, minPrice, maxPrice);

		return AdEntityViewConstants.SEARCH;
	}

	private final void loadViewModel(final ModelMap model, String city, String keywords, String interval,
			Double minPrice, Double maxPrice) {

		Iterable<AdEntity> adList = adEntityService.findAds(city, keywords != null ? keywords.trim() : null, interval,
				minPrice, maxPrice);
		Iterable<AdEntity> likedAds = likedAdService.getAdsLikedByUser(getLoggedUser(model));
		List<Integer> likesList = new ArrayList<>();

		likedAds.forEach(likedAd -> {
			likesList.add(likedAd.getId());
		});
		model.addAttribute("likesList", likesList);
		model.addAttribute("cities", adEntityService.getCities());
		model.put("user", getLoggedUser(model));
		model.put("follows", getLoggedUser(model).getFollowed());
		model.put(AdEntityViewConstants.PARAM_ENTITIES, adList);
	}
}