package es.udc.fi.dc.fd.controller.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.controller.ad.AdEntityViewConstants;
import es.udc.fi.dc.fd.controller.ad.AdSearchController;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.dto.SearchDto;
import es.udc.fi.dc.fd.model.dto.UserDto;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@Transactional
@RequestMapping("/rest")
public class AdSearchRESTController {

	
	private final AdEntityService adEntityService;
	private final LikeService likedAdService;
	private final UserService userEntityService;
	private final AdSearchController adSearchController;

	@Autowired
	public AdSearchRESTController(final LikeService likeService, final AdEntityService service,
			final UserService userService, AdSearchController searchController) {
		super();
		likedAdService = checkNotNull(likeService, "Received a null pointer as service");
		adEntityService = checkNotNull(service, "Received a null pointer as service");
		userEntityService = checkNotNull(userService, "Received a null pointer as service");
		adSearchController = checkNotNull(searchController, "Received a null pointer as controller");

	}
	

	@GetMapping(path = "/search")
	public Object findAds(@RequestParam(required = false, value = "city") String city,
			@RequestParam(required = false, value = "keywords") String keywords,
			@RequestParam(required = false, value = "interval") String interval,
			@RequestParam(required = false, value = "averageScore" ) Double averageScore,
			@RequestParam(required = false, value = "minPrice") Double minPrice,
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "maxPrice") Double maxPrice, final ModelMap model,
			HttpServletRequest request, final HttpServletResponse response) {
		String token = request.getParameter("token");
		
		UserEntity user = getLoggedUser(userName); 
		
		return adSearchController.loadViewModelREST(model, city, keywords, interval, averageScore, minPrice, maxPrice, user);
	}
	
	public UserEntity getLoggedUser(final String userName) {
		
		UserEntity user = userEntityService.findByUsername(userName);

		return user;
	}
	
	@GetMapping("userAPI") //Cambiarlo a POST, pero da forbidden preguntar a Laura
	public UserDto login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		
		String token = getJWTToken(username);
		User user = userEntityService.findByUsername(username);
		UserDto userDto = new UserDto(user.getUsername(), "");
		userDto.setToken(token);
		
		return userDto;
		
	}
	
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("scrolladJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
	

		return "Bearer " + token;
	}
}
