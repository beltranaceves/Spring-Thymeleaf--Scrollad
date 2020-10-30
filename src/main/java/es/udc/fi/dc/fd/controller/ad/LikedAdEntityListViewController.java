package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.fi.dc.fd.controller.entity.ExampleEntityViewConstants;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/advertisement")
public class LikedAdEntityListViewController {

	/**
	 * Example entity service.
	 */
	private final LikeService likedAdService;
	private final AdEntityService adService;
	private final UserService userService;

	@Autowired
	public LikedAdEntityListViewController(final LikeService likeService, final UserService userService,
			final AdEntityService adService) {
		super();
		this.likedAdService = checkNotNull(likeService, "Received a null pointer as service");
		this.adService = checkNotNull(adService, "Received a null pointer as service");
		this.userService = checkNotNull(userService, "Received a null pointer as service");
	}
	/*
	@PostMapping(path = "/addLike")
	public final void addRating(@RequestBody ParamsRatingDto params) {
		UserEntity user = userService.getFindById(params.getUser());
		AdEntity adLiked = adService.getFindById(params.getFriend());
		likedAdService.addLike(user, adLiked);
	}*/

	@GetMapping(path = "/likeList")
	public String showLikedAdEntityList(final ModelMap model) {

		loadViewModel(model);

		return AdEntityViewConstants.VIEW_LIKED_ENTITY_LIST;
	}
	
	@RequestMapping(value="/user", method = RequestMethod.GET)
	private final void loadViewModel(final ModelMap model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    
		UserEntity user = userService.findByUsername(auth.getName());
		
		model.put(AdEntityViewConstants.PARAM_ENTITIES, likedAdService.getAdsLikedByUser(user));
	}

}
