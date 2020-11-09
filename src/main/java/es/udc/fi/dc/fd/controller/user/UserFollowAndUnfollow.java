package es.udc.fi.dc.fd.controller.user;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.controller.entity.ExampleEntityViewConstants;
import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.form.UserForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;


@Controller
@RequestMapping("/followAndUnfollow")
public class UserFollowAndUnfollow {
	
	private final UserService userService;
	
	@Autowired
	public UserDetailsService userDetailsService;

	/**
	 * Constructs a controller with the specified dependencies.
	 * 
	 * @param service example entity service
	 */
	@Autowired
	public UserFollowAndUnfollow(final UserService service) {
		super();

		userService = checkNotNull(service, "Received a null pointer as service");
	}
	
	/*@ModelAttribute(ExampleEntityViewConstants.BEAN_FORM)
	public UserForm getUserForm() {
		return new UserForm();
	}*/
	
	@PostMapping(path= "/follow")
	public void saveFollow(final ModelMap model,
			@RequestParam(value="user", required=true) String user,
			@RequestParam(value="followed", required=true) String followed,
			final BindingResult bindingResult, final HttpServletRequest request, final HttpServletResponse response) {
		
		final UserEntity aux=userService.findByUsername(user);
		
		
		if (aux.getName()=="") {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
		} else {
			
			ArrayList<String> fol = (ArrayList<String>) aux.getFollowed();
			
			fol.add(followed);
			
			aux.setFollowed(fol);
			
		}
			
	}

	

	@PostMapping(path= "/unfollow")
	public void deleteFollow(final ModelMap model,
			@RequestParam(value="user", required=true) String user,
			@RequestParam(value="unfollowed", required=true) String unfollowed,
			final BindingResult bindingResult, final HttpServletRequest request, final HttpServletResponse response) {
		
		final UserEntity aux=userService.findByUsername(user);
		
		if (aux.getName()=="") {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			
			ArrayList<String> fol = (ArrayList<String>) aux.getFollowed();
			
			fol.remove(unfollowed);
			
			aux.setFollowed(fol);
			
			
		}
						
	}

	

}
