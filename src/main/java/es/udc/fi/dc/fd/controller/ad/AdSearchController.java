package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;

@Controller
@RequestMapping("/advertisement")
public class AdSearchController {

	private final AdEntityService adEntityService;

	@Autowired
	public AdSearchController(final AdEntityService service) {
		super();

		adEntityService = checkNotNull(service, "Received a null pointer as service");
	}

	@GetMapping(path = "/search")
	public String findAds(@RequestParam(required = false, value = "city") String city,
			@RequestParam(required = false, value = "keywords") String keywords, final ModelMap model) {

		loadViewModel(model, city, keywords);

		return AdEntityViewConstants.SEARCH;
	}

	private final void loadViewModel(final ModelMap model, String city, String keywords) {

		Iterable<AdEntity> adList = adEntityService.findAds(city, keywords != null ? keywords.trim() : null);

		model.addAttribute("cities", adEntityService.getCities());

		model.put(AdEntityViewConstants.PARAM_ENTITIES, adList);
	}
}