package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.udc.fi.dc.fd.model.dto.AdEntityDto;
import es.udc.fi.dc.fd.model.dto.SearchDto;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;



@Controller
@RequestMapping("/advertisement")
public class AdSearchController {

	
	private final UserService userEntityService;

	@Autowired
	public AdSearchController(final UserService userService) {
		super();
		userEntityService = checkNotNull(userService, "Received a null pointer as service");

	}
	
	public UserEntity getLoggedUser(final ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userEntityService.findByUsername(auth.getName());

		return user;
	}
	
	@GetMapping(path = "/search")
	public String findAds(@RequestParam(defaultValue = "", required = false, value = "city") String city,
			@RequestParam(defaultValue = "",required = false, value = "keywords") String keywords,
			@RequestParam(defaultValue = "", required = false, value = "interval") String interval,
			@RequestParam(required = false, value = "averageScore") Double averageScore,
			@RequestParam(required = false, value = "minPrice") Double minPrice,
			@RequestParam(defaultValue = "", required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "maxPrice") Double maxPrice, final ModelMap model,
			HttpServletRequest request, final HttpServletResponse response) {
		
		SearchDto searchDto = new SearchDto(null, null, null, null, null, null, null, null);
		String averageScoreParam = "";
		String minPriceParam = "";
		String maxPriceParam = "";
		if (averageScore != null) {
			averageScoreParam = averageScore.toString();
		}
		if (minPrice != null) {
			minPriceParam = minPrice.toString();
		}
		if (maxPrice != null) {
			maxPriceParam = maxPrice.toString();
		}
		String uri= "http://localhost:8080/rest/search?city=" + city + "&keywords=" + keywords +
				"&interval=" + interval + "&averageScore=" + averageScoreParam + "&minPrice=" + minPriceParam +
				"&userName=" + getLoggedUser(model).getUsername() + "&maxPrice" + maxPriceParam;
		
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request1 = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();
	    HttpResponse<String> response1;
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
			response1 =
			      client.send(request1, BodyHandlers.ofString());
			System.out.println("respuesta");
			searchDto = objectMapper.readValue(response1.body(), SearchDto.class);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    loadViewModel(model, searchDto.getLikesList(), searchDto.getCities(), searchDto.getUser(), searchDto.getScoreCount(), 
				searchDto.getScoredUsers(), searchDto.getFollows(), searchDto.getAdvertisements(), searchDto.getPremiumAdvertisements());
		
		
		return AdEntityViewConstants.SEARCH;
	}
	
	
	private final void loadViewModel(final ModelMap model, List<Integer> likesList, List<String> cities, UserEntity user, Integer scoredCount,
			Set<String> scoredUsers, Set<String> follows, List<AdEntityDto> advertisements, List<AdEntityDto> premiumAdvertisements) {

		
		model.addAttribute("likesList", likesList);
		model.addAttribute("cities", cities);
		model.put("user", user);
		model.put("scoreCount", scoredCount);
		model.put("scoredUsers", scoredUsers);
		model.put("follows", follows);
		model.put(AdEntityViewConstants.PARAM_ENTITIES, advertisements);
		model.put(AdEntityViewConstants.PARAM_PREMIUM_ENTITIES, premiumAdvertisements);
	}
	

}