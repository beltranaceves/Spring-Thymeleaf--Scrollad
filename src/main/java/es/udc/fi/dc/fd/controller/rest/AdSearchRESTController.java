package es.udc.fi.dc.fd.controller.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.controller.ad.AdSearchController;
import es.udc.fi.dc.fd.model.dto.SearchDto;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

@RestController
@Transactional
@RequestMapping("/rest")
public class AdSearchRESTController {

	private final AdEntityService adEntityService;
	private final LikeService likedAdService;
	private final UserService userEntityService;

	@Autowired
	public AdSearchRESTController(final LikeService likeService, final AdEntityService service,
			final UserService userService, AdSearchController searchController) {
		super();
		likedAdService = checkNotNull(likeService, "Received a null pointer as service");
		adEntityService = checkNotNull(service, "Received a null pointer as service");
		userEntityService = checkNotNull(userService, "Received a null pointer as service");

	}

	public UserEntity getUser(final String userName) {

		UserEntity user = userEntityService.findByUsername(userName);

		return user;
	}
	
	public UserEntity getLoggedUser(final ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userEntityService.findByUsername(auth.getName());

		return user;
	}

	@GetMapping(path = "/search")
	public Object findAds(@RequestParam(required = false, value = "city") String city,
			@RequestParam(required = false, value = "keywords") String keywords,
			@RequestParam(required = false, value = "interval") String interval,
			@RequestParam(required = false, value = "averageScore") Double averageScore,
			@RequestParam(required = false, value = "minPrice") Double minPrice,
			@RequestParam(defaultValue = "",required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "maxPrice") Double maxPrice, final ModelMap model,
			HttpServletRequest request, final HttpServletResponse response) {

		
		UserEntity user;
		
		if (userName == "") {
			user = getLoggedUser(model);
		} else {
			user = getUser(userName);
		}

		return loadViewModelREST(model, city, keywords, interval, averageScore, minPrice, maxPrice, user);
	}

	public final SearchDto loadViewModelREST(final ModelMap model, String city, String keywords, String interval,
			Double averageScore, Double minPrice, Double maxPrice, UserEntity user) {

		Iterable<AdEntity> adList = adEntityService.findAds(city, keywords != null ? keywords.trim() : null, interval,
				averageScore, minPrice, maxPrice);
		Iterable<AdEntity> likedAds = likedAdService.getAdsLikedByUser(user);
		List<Integer> likesList = new ArrayList<>();
		List<AdEntity> premiumAdList = new ArrayList<>();
		List<AdEntity> randomPremiumAdList = new ArrayList<>();
		List<AdEntity> normalAdList = new ArrayList<>();

		likedAds.forEach(likedAd -> {
			likesList.add(likedAd.getId());
		});

		adList.forEach(ad -> {
			if (ad.getUserA().getIsPremium()) {
				premiumAdList.add(ad);
			} else {
				normalAdList.add(ad);
			}
		});
		
		int size = premiumAdList.size();
		if (size < 5) {
			randomPremiumAdList = premiumAdList;
		} else {
			for (int i = 0; i < 5; i++) {
				randomPremiumAdList.add(premiumAdList.get((int) Math.floor(Math.random() * size)));
			}
		}
		
		SearchDto searchDto = new SearchDto(likesList, user.getScoreCount(), user, adEntityService.getCities(),
				user.getScored(), user.getFollowed(), normalAdList, randomPremiumAdList);
		return searchDto;
	}

}
