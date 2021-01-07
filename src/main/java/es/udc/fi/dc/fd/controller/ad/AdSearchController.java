package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.model.dto.SearchDto;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@Transactional
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

	public UserEntity getLoggedUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userEntityService.findByUsername(auth.getName());

		return user;
	}

	@GetMapping(path = "/search")
	public String findAds(@RequestParam(required = false, value = "city") String city,
			@RequestParam(required = false, value = "keywords") String keywords,
			@RequestParam(required = false, value = "interval") String interval,
			@RequestParam(required = false, value = "averageScore" ) Double averageScore,
			@RequestParam(required = false, value = "minPrice") Double minPrice,
			@RequestParam(required = false, value = "maxPrice") Double maxPrice, final ModelMap model) {

		loadViewModel(model, city, keywords, interval, averageScore, minPrice, maxPrice);

		return AdEntityViewConstants.SEARCH;
	}
	
	private final void loadViewModel(final ModelMap model, String city, String keywords, String interval,
			Double averageScore,Double minPrice, Double maxPrice) {

		Iterable<AdEntity> adList = adEntityService.findAds(city, keywords != null ? keywords.trim() : null, interval,
				averageScore,minPrice, maxPrice);
		Iterable<AdEntity> likedAds = likedAdService.getAdsLikedByUser(getLoggedUser());
		List<Integer> likesList = new ArrayList<>();
		List<AdEntity> premiumAdList= new ArrayList<>();
		List<AdEntity> randomPremiumAdList= new ArrayList<>();
		List<AdEntity> normalAdList= new ArrayList<>();
		
		likedAds.forEach(likedAd -> {
			likesList.add(likedAd.getId());
		});
		
		adList.forEach(ad -> {
			if(ad.getUserA().getIsPremium()) {
				premiumAdList.add(ad);
			} else {
				normalAdList.add(ad);
			}
		});
		int size = premiumAdList.size();
		if (size < 5) {
			randomPremiumAdList = premiumAdList;
		} else {
			for(int i = 0; i < 5; i++) {
				randomPremiumAdList.add(premiumAdList.get((int)Math.floor(Math.random()*size)));
			}
		}
		
		model.addAttribute("likesList", likesList);
		model.addAttribute("cities", adEntityService.getCities());
		model.put("user", getLoggedUser());
		model.put("scoreCount", getLoggedUser().getScoreCount());
		model.put("scoredUsers",getLoggedUser().getScored());
		model.put("follows", getLoggedUser().getFollowed());
		model.put(AdEntityViewConstants.PARAM_ENTITIES, normalAdList);
		model.put(AdEntityViewConstants.PARAM_PREMIUM_ENTITIES, randomPremiumAdList);
	}
	
	public final SearchDto loadViewModelREST(final ModelMap model, String city, String keywords, String interval,
			Double averageScore,Double minPrice, Double maxPrice, UserEntity user) {

		Iterable<AdEntity> adList = adEntityService.findAds(city, keywords != null ? keywords.trim() : null, interval,
				averageScore,minPrice, maxPrice);
		Iterable<AdEntity> likedAds = likedAdService.getAdsLikedByUser(user);
		List<Integer> likesList = new ArrayList<>();
		List<AdEntity> premiumAdList= new ArrayList<>();
		List<AdEntity> randomPremiumAdList= new ArrayList<>();
		List<AdEntity> normalAdList= new ArrayList<>();
		
		likedAds.forEach(likedAd -> {
			likesList.add(likedAd.getId());
		});
		
		adList.forEach(ad -> {
			if(ad.getUserA().getIsPremium()) {
				premiumAdList.add(ad);
			} else {
				normalAdList.add(ad);
			}
		});
		int size = premiumAdList.size();
		if (size < 5) {
			randomPremiumAdList = premiumAdList;
		} else {
			for(int i = 0; i < 5; i++) {
				randomPremiumAdList.add(premiumAdList.get((int)Math.floor(Math.random()*size)));
			}
		}
		
		
		Hibernate.initialize(user.getScoreCount());
		Hibernate.initialize(user.getScored());
		Hibernate.initialize(user.getFollowed());
		model.addAttribute("likesList", likesList);
		//model.addAttribute("cities", adEntityService.getCities());
		model.put("user", user);
		model.put("scoreCount", user.getScoreCount());
		model.put("scoredUsers", user.getScored());
		model.put("follows", user.getFollowed());
		model.put(AdEntityViewConstants.PARAM_ENTITIES, normalAdList);
		model.put(AdEntityViewConstants.PARAM_PREMIUM_ENTITIES, randomPremiumAdList);
		
		
		SearchDto searchDto = new SearchDto(likesList, user.getScoreCount(),user, adEntityService.getCities(), 
				user.getScored(), user.getFollowed(), normalAdList, randomPremiumAdList);
		return searchDto;
	}
}