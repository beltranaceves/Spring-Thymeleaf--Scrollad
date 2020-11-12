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

package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

/**
 * Controller for the example entities listing view.
 * <p>
 * This serves as an adapter between the UI and the services layer.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/advertisement")
public class AdEntityListViewController {

	private final LikeService likedAdService;
	private final AdEntityService adEntityService;
	private final UserService userService;

	@Autowired
	public AdEntityListViewController(final LikeService likeService, final UserService service,
			final AdEntityService adService) {
		super();
		likedAdService = checkNotNull(likeService, "Received a null pointer as service");
		adEntityService = checkNotNull(adService, "Received a null pointer as service");
		userService = checkNotNull(service, "Received a null pointer as service");
	}

	public UserEntity getLoggedUser(final ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userService.findByUsername(auth.getName());

		return user;
	}

	/**
	 * Shows the entities listing view.
	 * <p>
	 * Actually it just returns the name of the view. Spring will take care of the
	 * rest.
	 * <p>
	 * Before returning the name the model should be loaded with all the data
	 * required by the view.
	 * 
	 * @param model model map
	 * @return the name for the entities listing view
	 */
	@GetMapping(path = "/list")
	public String showAdEntityList(final ModelMap model) {
		// Loads required data into the model
		loadViewModel(model);

		return AdEntityViewConstants.VIEW_ENTITY_LIST;
	}

	@GetMapping(path = "/followed")
	public String showFollowedAdEntityList(final ModelMap model) {
		// Loads required data into the model
		loadViewModel(model);

		return AdEntityViewConstants.VIEW_FOLLOWED_ENTITY_LIST;
	}

	/**
	 * Loads the model data required for the entities listing view.
	 * <p>
	 * As the view will list all the entities, it requires these entities as one of
	 * the parameters.
	 * 
	 * @param model model map
	 */
	private final void loadViewModel(final ModelMap model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		final UserEntity userEntity = userService.findByUsername(username);
		model.put(AdEntityViewConstants.PARAM_ENTITIES, adEntityService.getAllEntities());
		model.put("user", userEntity);
		model.put("follows", userEntity.getFollowed());
	}

	private final void loadViewModelByUser(final ModelMap model, UserEntity user) {

		Iterable<AdEntity> adEntities = adEntityService.getEntitiesByUser(user);

		model.put(AdEntityViewConstants.PARAM_ENTITIES, adEntities);
	}

	@GetMapping(path = "/list/likes")
	public String showLikedAdEntityList(final ModelMap model) {

		loadViewModelByLike(model);

		return AdEntityViewConstants.VIEW_ENTITY_LIST_BY_LIKES;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	private final void loadViewModelByLike(final ModelMap model) {

		model.put(AdEntityViewConstants.PARAM_ENTITIES, likedAdService.getAdsLikedByUser(getLoggedUser(model)));
	}

	@PostMapping(path = "/addLike")
	public String addRating(final ModelMap model,
			@ModelAttribute(AdEntityViewConstants.PARAM_ENTITY) @Valid final Integer adId) {
		UserEntity user = userService.findById(getLoggedUser(model).getId());
		AdEntity adLiked = adEntityService.findById(adId);
		if (likedAdService.addLike(user, adLiked) != null)
			return AdEntityViewConstants.ADD_LIKED_AD_SUCCESS;
		else
			return AdEntityViewConstants.ADD_LIKED_AD_UNSUCCESS;
	}

	@PostMapping(path = "/deleteLike")
	public String deleteLikeEntity(final ModelMap model,
			@ModelAttribute(AdEntityViewConstants.PARAM_ENTITY) @Valid final Integer adId) {
		likedAdService.deleteByAdLikedIdAndUserId(adId, getLoggedUser(model).getId());
		return AdEntityViewConstants.DELETE_LIKED_AD_SUCCESS;
	}

	@GetMapping(path = "/list/myAdvertisements")
	public String showAdEntityListByUser(final ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		UserEntity user = userService.findByUsername(username);

		loadViewModelByUser(model, user);

		return AdEntityViewConstants.VIEW_ENTITY_LIST_BY_USER;
	}

	@PostMapping(path = "/delete")
	public String deleteAdEntity(final ModelMap model,
			@ModelAttribute(AdEntityViewConstants.PARAM_ENTITY) @Valid final Integer adEntityId) {
		adEntityService.deleteById(adEntityId);
		return AdEntityViewConstants.DELETE_AD_SUCCESS;
	}

	@PostMapping(path = "/updateIsOnHold")
	public String updateAdEntityIsOnHold(final ModelMap model,
			@ModelAttribute(AdEntityViewConstants.PARAM_ENTITY) @Valid final Integer adEntityId) {
		adEntityService.updateIsOnHoldById(adEntityId);
		return AdEntityViewConstants.ISONHOLD_CHANGE_SUCCESS;
	}

}
