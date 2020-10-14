package es.udc.fi.dc.fd.controller.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.udc.fi.dc.fd.service.AdEntityService;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.form.AdForm;


@Controller
@RequestMapping("/advertisement")
public class AdEntityFormController {
	
    private final AdEntityService adEntityService;

    
    @Autowired
    public AdEntityFormController(final AdEntityService service) {
        super();

        adEntityService = checkNotNull(service,
                "Received a null pointer as service");
    }
    
    @ModelAttribute(AdEntityViewConstants.BEAN_FORM)
    public AdForm getAdForm() {
        return new AdForm();
    }
    
    @PostMapping
    public String saveEntity(final ModelMap model,
            @ModelAttribute(AdEntityViewConstants.BEAN_FORM) @Valid final AdForm form,
            final BindingResult bindingResult, final HttpServletResponse response) {
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
            entity.setImage(form.getImage());
            

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
        model.put(AdEntityViewConstants.PARAM_ENTITIES,
        		adEntityService.getAllEntities());
    }
}
