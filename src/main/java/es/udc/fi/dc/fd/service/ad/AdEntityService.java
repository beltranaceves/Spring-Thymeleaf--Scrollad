package es.udc.fi.dc.fd.service.ad;

import org.springframework.data.domain.Pageable;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;

public interface AdEntityService {


    public Ad add(final AdEntity entity);


    public AdEntity findById(final Integer identifier);

    
    public Iterable<AdEntity> getAllEntities();


    public Iterable<AdEntity> getEntities (final Pageable page);


    public void remove(final AdEntity advertisement);
    
    
    public boolean checkForm(final AdForm adForm); 

}
