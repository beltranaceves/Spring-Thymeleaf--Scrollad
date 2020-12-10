package es.udc.fi.dc.fd.controller.order;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.form.OrderForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.order.OrderService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/order")
public class OrderFormController {

	private final OrderService orderEntityService;

	private final UserService userEntityService;
	
	private final AdEntityService adEntityService;
	
	@Autowired
	public OrderFormController(final AdEntityService adService, final OrderService orderService,
			final UserService userService) {
		super();

		orderEntityService = checkNotNull(orderService, "Received a null pointer as service");

		userEntityService = checkNotNull(userService, "Received a null pointer as service");
		
		adEntityService = checkNotNull(adService, "Received a null pointer as service");
	}
	
	@ModelAttribute(OrderViewConstants.BEAN_FORM)
	public OrderForm getAdForm() {
		return new OrderForm();
	}
	
	public UserEntity getLoggedUser(final ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userEntityService.findByUsername(auth.getName());

		return user;
	}
	
	@PostMapping
	public String saveEntity(final ModelMap model,
			@ModelAttribute(OrderViewConstants.BEAN_FORM) @Valid final OrderForm form,
			final BindingResult bindingResult, final HttpServletResponse response){
		final String path;
		final OrderEntity entity;
		

		if (orderEntityService.checkForm(form)) {
			// Invalid form data

			// Returns to the form view
			path = OrderViewConstants.VIEW_ENTITY_FORM;

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {

			entity = new OrderEntity();
			entity.setAddress(form.getAddress());
			entity.setCreditCard(form.getCreditCard());
			entity.setDate(LocalDateTime.now());
			entity.setAd(adEntityService.findById(form.getAdId()));
			entity.setPrice(adEntityService.findById(form.getAdId()).getPrice());
			

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String username;

			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}

			UserEntity user = userEntityService.findByUsername(username);
			entity.setUser(user);

			orderEntityService.addOrder(entity);
			
			adEntityService.updateIsSoldById(form.getAdId());

			loadViewModel(model);

			path = OrderViewConstants.VIEW_ENTITY_LIST;
		}

		return path;
	}
	
	@PostMapping(path = "/buyPremium")
	public String buyPremium(final ModelMap model,
			@ModelAttribute(OrderViewConstants.BEAN_FORM) @Valid final OrderForm form,
			final BindingResult bindingResult, final HttpServletResponse response){
		final String path;
		final OrderEntity entity;
		final AdEntity adEntity;
		
		
		if (orderEntityService.checkForm(form)) {
			// Invalid form data

			// Returns to the form view
			path = OrderViewConstants.VIEW_ENTITY_FORM;

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			adEntity = new AdEntity();
			adEntity.setTitle("Premium");
			adEntity.setDescription("Premium Subscription");
			adEntity.setDate(LocalDateTime.now());
			adEntity.setIsOnHold(true);
			adEntity.setIsSold(false);
			adEntity.setPrice(20.0);
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String username;

			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}

			UserEntity user = userEntityService.findById(0);
			adEntity.setUserA(user);

			
			Ad ad = adEntityService.add(adEntity);
			
			form.setAdId(ad.getId());
			
			entity = new OrderEntity();
			entity.setAddress(form.getAddress());
			entity.setCreditCard(form.getCreditCard());
			entity.setDate(LocalDateTime.now());
			entity.setAd(adEntityService.findById(form.getAdId()));
			entity.setPrice(adEntityService.findById(form.getAdId()).getPrice());
			
			
			user = userEntityService.findByUsername(username);
			
			entity.setUser(user);

			orderEntityService.addOrder(entity);
			
			adEntityService.updateIsSoldById(form.getAdId());

			user.setIsPremium(true);
			
			userEntityService.updateIsPremiumUserByUserId(user.getId(), true);
			
			loadViewModel(model);

			path = OrderViewConstants.VIEW_ENTITY_LIST;
		}

		return path;
	}
	
	@GetMapping(path = "/premiumForm")
	public String showPremiumForm(final ModelMap model) {
		String path = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		UserEntity user = userEntityService.findByUsername(username);
		
		if(user.getIsPremium()) {
			path = OrderViewConstants.VIEW_ENTITY_ALREADY_PREMIUM;
		} else {
			path = OrderViewConstants.VIEW_ENTITY_FORM_PREMIUM;	
		}
		return path;	
	}
	
	private final void loadViewModel(final ModelMap model) {
		model.put(OrderViewConstants.PARAM_ENTITIES, orderEntityService.getEntitiesByUser(getLoggedUser(model)));
	}
	
	@GetMapping(path = "/edit/{advertisementId}")
	public String showEntityForm(final ModelMap model,
			@PathVariable(value = "advertisementId") Integer advertisementId) {
		
		loadViewModelSingle(model, adEntityService.findById(advertisementId));
		
		return OrderViewConstants.VIEW_ENTITY_FORM;
	}
	
	private final void loadViewModelSingle(final ModelMap model, AdEntity ad) {
		model.put(OrderViewConstants.PARAM_ENTITY, ad);
	}

	


}
