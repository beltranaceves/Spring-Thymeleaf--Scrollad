package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/advertisement")
public class AdEntityFormController {
	
	private final AdEntityService adEntityService;

	private final UserService userEntityService;

	@Autowired
	public AdEntityFormController(final AdEntityService service, final UserService userService) {
		super();

		adEntityService = checkNotNull(service, "Received a null pointer as service");

		userEntityService = checkNotNull(userService, "Received a null pointer as service");
	}

	@ModelAttribute(AdEntityViewConstants.BEAN_FORM)
	public AdForm getAdForm() {
		return new AdForm();
	}

	@PostMapping
	public String saveEntity(final ModelMap model,
			@ModelAttribute(AdEntityViewConstants.BEAN_FORM) @Valid final AdForm form,
			final BindingResult bindingResult, final HttpServletResponse response,
			@RequestParam(value = "file", required = true) Part file) {
		final String path;
		final AdEntity entity;

		if (adEntityService.checkForm(form)) {
			// Invalid form data

			// Returns to the form view
			path = AdEntityViewConstants.VIEW_ENTITY_FORM;

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {

			entity = new AdEntity();
			entity.setTitle(form.getTitle());
			entity.setDescription(form.getDescription());
			entity.setDate(LocalDateTime.now());

			if (file != null) {
				byte[] filecontent = null;
				try {
					InputStream inputStream = file.getInputStream();
					filecontent = IOUtils.toByteArray(inputStream);
					entity.setImage(filecontent);
				} catch (IOException ex) {
				}
			}

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String username;

			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}

			UserEntity user = userEntityService.findByUsername(username);
			entity.setUserA(user);
			adEntityService.add(entity);

			loadViewModel(model);

			path = AdEntityViewConstants.VIEW_AD_SUCCESS;
		}

		return path;
	}

	@GetMapping(path = "/edit")
	public String showEntityForm() {
		return AdEntityViewConstants.VIEW_ENTITY_FORM;
	}

	private final void loadViewModel(final ModelMap model) {
		model.put(AdEntityViewConstants.PARAM_ENTITIES, adEntityService.getAllEntities());
	}
}
